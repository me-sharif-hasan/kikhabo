package com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.FamilyDataSource;
import com.iishanto.kikhabo.domain.datasource.PreferenceDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.domain.entities.meal.AvailableIngredient;
import com.iishanto.kikhabo.domain.entities.weather.Weather;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import com.iishanto.kikhabo.domain.entities.people.Preference;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.PromptProvider;
import com.iishanto.kikhabo.infrastructure.services.health.HealthServices;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Component
public class GeminiPromptImpl implements PromptProvider {
    Logger logger;
    UserDataSource userDataSource;
    FamilyDataSource familyDataSource;
    HealthServices healthServices;
    WeatherDataSource weatherDataSource;
    PreferenceDataSource preferenceDataSource;

    @Override
    public String getPrompt(Prompt prompt) {
        User user=userDataSource.getAuthenticatedUser();
        String promptString = """
                {
                    "contents": [{
                        "parts":[
                            {
                                "text": "%s"
                            },
                            {
                                "text": "I have already eaten these items in my meal. So I want you to consider these and prepare the meal
                                and grocery suggestion according to the following prompt so that the suggestion do not become boring. Try to serve
                                different kinds of food, not just flavors of these, %s. Also consider giving healthy food according to the BMI I'll give in
                                the following prompt"
                            },
                            {
                                "text": "Here is the current weather information that might be helpful to generate the meal suggestions. You should suggest best meal within the budget range
                                that suites the weather. The weather information: %s"
                            },
                            {
                                "text": "%s"
                            },
                            {
                                "text": "Always considers the preferences. Specially if anyone have diseases and if there is a kid. Always try to give healthiest food possible. Here is the preferences of the family members: %s"
                            },
                            {
                                "text": "%s"
                            }
                        ]
                    }],
                    "generationConfig": {
                        "temperature": 2.0
                     }
                }
                """.formatted(
                        getUserPrompt(prompt,user),
                        StringUtils.join(prompt.getLastMealRecord().stream().map(Meal::getMealName).toList(),", "),
                        getWeatherContext(user),
                        getCulturalStapleInstruction(user),
                        getFamilyPreferences(),
                        getAvailableIngredientsText(prompt)
        );
        logger.warn(promptString);
        return promptString;
    }
    private String getUserPrompt(Prompt prompt,User user) {
        Preference preference;
        try {
            logger.info("loading saved preference");
            preference=preferenceDataSource.getPreference();
            logger.info("loaded saved preference");
        }catch (Exception e){
            logger.info("setting default preference");
            preference = new Preference();
            preference.setBudgetRating(5);
            preference.setSaltTasteRating(3);
            preference.setSpicyRating(4);
        }
        return
                """
                Generator seed: %d,
                You are a server who serves his response using only JSON. Your role is to generate %d meal suggestions and you must plan the groceries to buy.
                Here are some specifications about the person(s) who will consume your result.
                Age list of the person(s): %s,
                Average BMI of the person(s): %s,
                Spicy rating of the meal (1–10, higher means spicier): %f,
                Salt rating of the meal (1–10, higher means saltier): %f,
                Budget level: %s,
                Current season: it is %s of %s, so factor in seasonal ingredient availability,
                Country of origin: %s,
                Language to respond meal names and notes in: %s

                Also consider preferences of the family members.

                %s

                %s

                You must follow this JSON schema structure in order to be successfully parsed by the mobile client:
                        {
                                            status:<response status, success|error>,
                                            message:<status message>,
                                            data:{
                                            totalMeals:<the number of meals to generate>,
                                                meals:[
                                                    {
                                                        mealName:<name of the meal>,
                                                        totalEnergy:<total energy in kilocalorie| in float, this field is required>,
                                                        note:<any disclaimer or preparation note, keep empty if not necessary>,
                                                        ingredients:<comma separated list of ingredients>,
                                                        youtubeSearchTerms:[<array of 2-3 YouTube search query strings to find cooking tutorials for this meal's key components>],
                                                        groceries:[
                                                             {
                                                                 name:<name of the grocery item>,
                                                                 priceRatingOutOf10:<price rating out of 10>,
                                                                 amountInGm:<how much to buy in grams>
                                                             },
                                                             <list of remaining groceries>
                                                        ]
                                                    },
                                                    <list of remaining meals>
                                                ]
                                            }
                                        }
                The JSON must be valid and all strings and keys must be quoted. Randomize the meal selection in each request using the generator seed. Keep notes empty if not necessary.
                Do not make the suggestion boring. %s Here are the last %d meals already eaten:
                %s. Be concise — just mention names and give short, consistent sentences.
                """.formatted(
                        System.currentTimeMillis(),
                        getMealCount(preference,prompt),
                        getAgesOfTheMembers(preference,prompt),
                        getBmiContext(),
                        getSpicyRating(preference,prompt),
                        getSaltRating(preference,prompt),
                        getBudgetDescription(getPriceRating(preference,prompt), user.getCountry()),
                        getMonthName(Calendar.getInstance().get(Calendar.MONTH)),
                        getCountryText(user),
                        getCountryText(user),
                        getLanguageForCountry(user.getCountry()),
                        getReligionInstruction(user),
                        getGenderInstruction(user),
                        getFoodCultureInstruction(user),
                        prompt.getLastMealRecord().size(),
                        StringUtils.join(prompt.getLastMealRecord().stream().map(Meal::getMealName).toList(),", ")
                );
    }

    private String getAvailableIngredientsText(Prompt prompt) {
        List<AvailableIngredient> ingredients = prompt.getMealPreferenceData().getAvailableIngredients();
        if (ingredients == null || ingredients.isEmpty()) {
            return "The user has not specified any ingredients they already have at home.";
        }
        String list = ingredients.stream()
                .filter(i -> i != null && i.getName() != null && !i.getName().isBlank())
                .map(i -> {
                    String qty = (i.getQuantity() == null || i.getQuantity().isBlank()) ? "unspecified amount" : i.getQuantity();
                    return i.getName() + " (" + qty + ")";
                })
                .collect(java.util.stream.Collectors.joining(", "));
        if (list.isBlank()) {
            return "The user has not specified any ingredients they already have at home.";
        }
        return "The user already has these ingredients at home: " + list + ". " +
                "Prioritize building meals around these to avoid waste and save money. " +
                "For each suggested meal, try to incorporate as many of these available ingredients as possible. " +
                "You may suggest additional groceries to complement them, but keep extra purchases minimal.";
    }

    private String getCommaSeperatedBmiList(){
        List <User> members=familyDataSource.getFamilyMembersOfCurrentUser();
        List <Double> bmiList=members.stream().map(member->member.getBmi(healthServices)).toList();
        return StringUtils.join(bmiList, ", ");
    }
    private int getMealCount(Preference preference,Prompt prompt) {
        List <User> familyMembers=familyDataSource.getFamilyMembersOfCurrentUser();
        logger.info("total family members: {}", familyMembers.size());
        if(prompt.getMealPreferenceData().getTotalMealCount()==null){
            Float numDays=prompt.getMealPreferenceData().getDayCount();
            Integer mealPerDay=prompt.getMealPreferenceData().getMealPerDay();
            if(numDays!=null&&mealPerDay==null){
                return (int) (3*familyMembers.size()*numDays);
            }
            if(numDays==null&&mealPerDay!=null){
                return mealPerDay*familyMembers.size();
            }
            if(numDays == null){
                return 3*familyMembers.size();
            }
            return 3;
        }else{
            return prompt.getMealPreferenceData().getTotalMealCount();
        }
    }
    private String getAgesOfTheMembers(Preference preference,Prompt prompt) {
        if(prompt.getMealPreferenceData().getAgesOfTheMembers()!=null&& !prompt.getMealPreferenceData().getAgesOfTheMembers().isEmpty()){
            logger.info("getting age list from manual preference");
            return StringUtils.join(prompt.getMealPreferenceData().getAgesOfTheMembers(),',');
        }else{
            logger.info("getting age list from automatic preference");
            List <User> familyMembers=familyDataSource.getFamilyMembersOfCurrentUser();
            List <String> ages=new ArrayList<>();
            for(User user:familyMembers){
                try{
                    ages.add(user.getAge());
                }catch (Exception e){
                    ages.add("20");
                }
            }
            return StringUtils.join(ages,",");
        }
    }
    private float getSpicyRating(Preference preference, Prompt prompt) {
        return prompt.getMealPreferenceData().getSpicyRating()==null?preference.getSpicyRating():prompt.getMealPreferenceData().getSpicyRating();
    }
    private String getFamilyPreferences(){
        List <User> members=familyDataSource.getFamilyMembersOfCurrentUser();
        List <Preference> preferences=new ArrayList<>();
        for(User user:members){
            preferences.add(user.getPreference());
        }
        return StringUtils.join(preferences,",");
    }
    private float getSaltRating(Preference preference, Prompt prompt) {
        return prompt.getMealPreferenceData().getSaltRating()==null?preference.getSaltTasteRating():prompt.getMealPreferenceData().getSaltRating();
    }
    private float getPriceRating(Preference preference, Prompt prompt) {
        return prompt.getMealPreferenceData().getPriceRating()==null?preference.getBudgetRating():prompt.getMealPreferenceData().getPriceRating();
    }
    private String getMonthName(int index){
        String []months=new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
        return months[index];
    }

    private String getLanguageForCountry(String country) {
        if (country == null) return "English";
        return Locale.availableLocales()
                .filter(l -> !l.getLanguage().isEmpty() && !l.getCountry().isEmpty())
                .filter(l -> l.getDisplayCountry(Locale.ENGLISH).equalsIgnoreCase(country.trim()))
                // prefer non-English locale so we get the native language name
                .min(Comparator.comparingInt(l -> l.getLanguage().equals("en") ? 1 : 0))
                .map(l -> l.getDisplayLanguage(Locale.ENGLISH))
                .orElse("English");
    }

    private String getCurrencyForCountry(String country) {
        if (country == null) return "USD";
        return Locale.availableLocales()
                .filter(l -> !l.getCountry().isEmpty())
                .filter(l -> l.getDisplayCountry(Locale.ENGLISH).equalsIgnoreCase(country.trim()))
                .findFirst()
                .map(l -> {
                    try { return Currency.getInstance(l).getCurrencyCode(); }
                    catch (Exception e) { return "USD"; }
                })
                .orElse("USD");
    }

    // ── Null-safe profile context helpers ────────────────────────────────────

    /** Returns the country string, or a fallback that tells the AI to use globally common food. */
    private String getCountryText(User user) {
        if (user.getCountry() == null || user.getCountry().isBlank()) {
            return "unspecified (assume globally common, internationally popular food)";
        }
        return user.getCountry();
    }

    /**
     * Returns the religion dietary instruction.
     * If religion is missing, instructs the AI to suggest food that is safe for any religion.
     */
    private String getReligionInstruction(User user) {
        if (user.getReligion() == null || user.getReligion().isBlank()) {
            return "The user's religion is not specified. Suggest food that is religiously neutral and universally acceptable — avoid pork, beef, and other ingredients that are restricted by any major religion, unless the meal is explicitly labelled as optional. Prefer chicken, fish, vegetables, legumes, and eggs as protein sources.";
        }
        return "Always remember to provide meals according to religion. Ex. Muslims do not eat pork, Hindus do not eat beef. For your information, the religion of the user is " + user.getReligion() + ".";
    }

    /**
     * Returns a gender-based nutritional hint.
     * If gender is missing, skips gender-specific advice.
     */
    private String getGenderInstruction(User user) {
        if (user.getGender() == null || user.getGender().isBlank()) {
            return "The user's gender is not specified; apply general balanced nutrition guidelines.";
        }
        String g = user.getGender().trim().toLowerCase();
        if (g.equals("male")) {
            return "The user is male. Consider slightly higher protein and caloric needs typical for adult males.";
        }
        if (g.equals("female")) {
            return "The user is female. Consider nutritional needs typical for adult females, including adequate iron and calcium-rich options.";
        }
        return "Apply general balanced nutrition guidelines for the user.";
    }

    /**
     * Returns weather context as a human-readable string, or a neutral fallback if country is unknown.
     */
    private String getWeatherContext(User user) {
        if (user.getCountry() == null || user.getCountry().isBlank()) {
            return "Weather information is unavailable because the user's country is not specified. Suggest meals suitable for any climate.";
        }
        try {
            Weather weather = weatherDataSource.getWeather(user.getCountry());
            return "Location: %s, Temperature: %.1f°C (feels like %.1f°C), Min: %.1f°C, Max: %.1f°C, Humidity: %.0f%%, Wind speed: %.1f m/s, Cloudiness: %d%%, Season: %s"
                    .formatted(
                            weather.getLocationName(),
                            weather.getTemperature(),
                            weather.getFeelsLike(),
                            weather.getTempMin(),
                            weather.getTempMax(),
                            weather.getHumidity(),
                            weather.getWindSpeed(),
                            weather.getCloudiness(),
                            weather.getSeason()
                    );
        } catch (Exception e) {
            logger.warn("Could not fetch weather for country={}", user.getCountry());
            return "Weather data is temporarily unavailable. Suggest meals suitable for a temperate climate.";
        }
    }

    /**
     * Returns the cultural staple instruction, or a global-food fallback when country is unknown.
     */
    private String getCulturalStapleInstruction(User user) {
        if (user.getCountry() == null || user.getCountry().isBlank()) {
            return "The user's country is not specified. Suggest internationally popular, culturally diverse meals such as rice dishes, pasta, bread-based meals, or stir-fries. Respond to the user in English.";
        }
        return "Always include the staple/common food for the user's country. For example, Bangladeshi people almost always take rice with their meals, Indians often have roti or rice, etc. Use your knowledge of " + user.getCountry() + " cuisine to add culturally appropriate staples. Respond to the user in " + getLanguageForCountry(user.getCountry()) + ".";
    }

    /**
     * Returns a food-culture instruction for the final sentence.
     * Falls back to globally common food when country is unknown.
     */
    private String getFoodCultureInstruction(User user) {
        if (user.getCountry() == null || user.getCountry().isBlank()) {
            return "Suggest internationally popular foods that are affordable and widely enjoyed across different cultures.";
        }
        return "Only suggest foods that people from " + user.getCountry() + " actually eat day to day, that are culturally appropriate and affordable within the given budget.";
    }

    /**
     * Returns BMI list, with a safe fallback if all members have unknown body metrics.
     */
    private String getBmiContext() {
        try {
            String bmi = getCommaSeperatedBmiList();
            if (bmi == null || bmi.isBlank()) {
                return "unknown (body metrics not provided; apply standard healthy BMI guidelines)";
            }
            return bmi;
        } catch (Exception e) {
            return "unknown (body metrics not provided; apply standard healthy BMI guidelines)";
        }
    }

    private String getBudgetDescription(float rating, String country) {
        String currency = getCurrencyForCountry(country);
        boolean isBangladesh = country != null && country.equalsIgnoreCase("bangladesh");
        if (isBangladesh) {
            if (rating <= 3) return String.format("low (100–300 %s per meal)", currency);
            if (rating <= 7) return String.format("medium (301–700 %s per meal)", currency);
            return String.format("high (up to 4000 %s per meal)", currency);
        }
        // For other countries instruct AI to apply local cost knowledge for the budget tier
        if (rating <= 3) return String.format("low budget — affordable street food / home-cooked level in %s (%s)", country, currency);
        if (rating <= 7) return String.format("medium budget — mid-range local food level in %s (%s)", country, currency);
        return String.format("high budget — premium dining level in %s (%s)", country, currency);
    }

}
