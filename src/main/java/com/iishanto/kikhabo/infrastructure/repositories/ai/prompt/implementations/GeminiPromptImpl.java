package com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@Component
public class GeminiPromptImpl implements PromptProvider {
    ObjectMapper objectMapper;
    Logger logger;
    UserDataSource userDataSource;
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
                        weatherDataSource.getWeather(user.getCountry())
        );
        logger.warn(promptString);
        return promptString;
    }
    private String getUserPrompt(Prompt prompt,User user) {
        Preference preference=new Preference();
        try {
            preferenceDataSource.getPreference();
        }catch (Exception e){
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
                        healthServices.getBMI(user),
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

    private int getMealCount(Preference preference,Prompt prompt) {
        return prompt.getMealPreferenceData().getTotalMealCount()==null?1:prompt.getMealPreferenceData().getTotalMealCount();
    }
    private String getAgesOfTheMembers(Preference preference,Prompt prompt) {
        //todo: fetch age list from the family members
        return StringUtils.join(prompt.getMealPreferenceData().getAgesOfTheMembers(),',');
    }
    private float getSpicyRating(Preference preference, Prompt prompt) {
        return prompt.getMealPreferenceData().getSpicyRating()==null?preference.getSpicyRating():prompt.getMealPreferenceData().getSpicyRating();
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
