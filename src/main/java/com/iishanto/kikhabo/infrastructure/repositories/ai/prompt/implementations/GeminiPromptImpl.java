package com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.PromptProvider;
import com.iishanto.kikhabo.infrastructure.services.health.HealthServices;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GeminiPromptImpl implements PromptProvider {
    ObjectMapper objectMapper;
    Logger logger;
    UserDataSource userDataSource;
    HealthServices healthServices;
    WeatherDataSource weatherDataSource;

    @Override
    public String getPrompt(Prompt prompt) {

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
                            }
                        ]
                    }],
                    "generationConfig": {
                        "temperature": 2.0
                     }
                }
                """.formatted(
                        getUserPrompt(prompt),
                        StringUtils.join(prompt.getLastMealRecord().stream().map(Meal::getMealName).toList(),", ")
        );
        logger.warn(promptString);
        return promptString;
    }
    private String getUserPrompt(Prompt prompt) {
        User user=userDataSource.getAuthenticatedUser();
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
                Current season: %s,
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
                                                        totalEnergy:<total energy in kilocalorie| in float>,
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
                        prompt.getMealPreferenceData().getTotalMealCount(),
                        StringUtils.join(prompt.getMealPreferenceData().getAgesOfTheMembers(),','),
                        healthServices.getBMI(user),
                        prompt.getMealPreferenceData().getSpicyRating() /*Spicy rating*/,
                        prompt.getMealPreferenceData().getSaltRating() /*Salt rating of the meal*/,
                        prompt.getMealPreferenceData().getPriceRating() /*Budget rating*/,
                        weatherDataSource.getSeason(user.getCountry())/*Season*/,
                        user.getCountry() /*Country*/,
                        user.getReligion(),
                        prompt.getLastMealRecord().size(),
                        StringUtils.join(prompt.getLastMealRecord().stream().map(Meal::getMealName).toList(),", ")
                );
    }
}
