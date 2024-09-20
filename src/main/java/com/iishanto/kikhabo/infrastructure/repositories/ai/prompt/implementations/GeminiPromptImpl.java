package com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.FamilyDataSource;
import com.iishanto.kikhabo.domain.datasource.PreferenceDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
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
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Component
public class GeminiPromptImpl implements PromptProvider {
    ObjectMapper objectMapper;
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
                                "text": "Always include the common food for the country. for example, Bangladeshi people almost always take rice with there meals. So, use your commonsense to add something like that."
                            },
                            {
                                "text": "Always considers the preferences. Specially if anyone have diseases and if there is a kid. Always try to give healthiest food possible. Here is the preferences of the family members: %s"
                            },
                            {
                                "text": "Make all responses in bengali"
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
                        weatherDataSource.getWeather(user.getCountry()),
                        getFamilyPreferences()
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
                You are a server who server his response by only JSON. Your role is to generate %d meal suggestion and you must plan the groceries to buy.
                Here are some specification about the person(s) who will consume your result.
                Age list of the person(s): %s,
                Average BMI of the person(s): %s,
                Spicy rating of the meal: %f,
                Salt rating of the meal: %f,
                Budget rating out of 10: %f,
                Current season: its %s of %s, so whatever season this is,
                Country of origin: %s
                
                Also consider preferences of the family members
                
                Always remember to provide meals according to religion. Ex. Muslims do not eat pork, Hindus do not eat cows. For your information, religion of the user is %s.
                
                You must follow this JSON schema structure inorder to be successfully parsed by mobile client
                        {
                                            status:<response status, success|error>,
                                            message:<status message>,
                                            data:{
                                            totalMeals:<the number of meal to generate>,
                                                meals:[
                                                    {
                                                        mealName:<name of the meal>,
                                                        totalEnergy:<total energy in kilocalorie| in float, this field is must>,
                                                        note:<if any disclaimer needed to be maintained>,
                                                        ingredients:<comma seperated list of ingredients>,
                                                        groceries:[
                                                             {
                                                                 name:<name of the grocery item>,
                                                                 priceRatingOutOf10:<price rating>,
                                                                 amountInGm:<how much to buy>
                                                             },
                                                             <list of remaining groceries>
                                                        ]
                                                    },
                                                    <list of remaining meal>
                                                ]
                                            }
                                        }
                The json should be a valid json and strings and keys should be quoted. Also, randomize meal in each request. Consider the generator seed in this case. Also keep the note empty if not that necessary.
                One last request, please don't make the suggestion boring and only suggest foods that bangladeshi peoples eat day by day and affordable according to price rating. For your use, here are the last %d meal list:
                %s. Also you don't have to say too much, just mention the names and give consistent sentences.
                """.formatted(
                        System.currentTimeMillis(),
                        getMealCount(preference,prompt),
                        getAgesOfTheMembers(preference,prompt),
                        getCommaSeperatedBmiList(),
                        getSpicyRating(preference,prompt) /*Spicy rating*/,
                        getSaltRating(preference,prompt) /*Salt rating of the meal*/,
                        getPriceRating(preference,prompt) /*Budget rating*/,
                        getMonthName(Calendar.getInstance().get(Calendar.MONTH)),
                        user.getCountry(),
                        user.getCountry() /*Country*/,
                        user.getReligion(),
                        prompt.getLastMealRecord().size(),
                        StringUtils.join(prompt.getLastMealRecord().stream().map(Meal::getMealName).toList(),", ")
                );
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

}
