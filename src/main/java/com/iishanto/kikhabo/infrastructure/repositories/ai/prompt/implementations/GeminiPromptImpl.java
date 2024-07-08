package com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.PromptProvider;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class GeminiPromptImpl implements PromptProvider {
    ObjectMapper objectMapper;
    Logger logger;
    UserRepository userRepository;

    @Override
    public String getPrompt(Prompt prompt) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        logger.info("EMAIL II: {}",userDetails.getUsername());
        UserEntity userEntity =userRepository.findByEmail(userDetails.getUsername());
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
        return
                """
                Generator seed: %d,
                You are a server who server his response by only JSON. Your role is to generate %d meal suggestion and you must plan the groceries to buy.
                Here are some specification about the person(s) who will consume your result.
                Number of person(s): %d,
                Age list of the person(s): %s,
                BMI list of the person(s): %s,
                Average working hours list of each person: %s,
                Spicy rating of the meal: %f,
                Sweetness rating of the meal: %f,
                Salt rating of the meal: %f,
                Budget rating out of 10: %f,
                Current season: %s,
                Country of origin: %s
                
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
                        4/*Number of total person*/,
                        "20,22,23,21" /*Age list of the persons*/,
                        "18,20,30,32" /*BMI list of the persons*/,
                        "10,5,10,7" /*Working hours list of the persons*/,
                        prompt.getMealPreferenceData().getSpicyRating() /*Spicy rating*/,
                        3.2 /*Sweetness rating*/,
                        prompt.getMealPreferenceData().getSaltRating() /*Salt rating of the meal*/,
                        prompt.getMealPreferenceData().getPriceRating() /*Budget rating*/,
                        "Winter" /*Season*/,
                        "Bangladesh" /*Country*/,
                        prompt.getLastMealRecord().size(),
                        StringUtils.join(prompt.getLastMealRecord().stream().map(Meal::getMealName).toList(),", ")
                );
    }
}
