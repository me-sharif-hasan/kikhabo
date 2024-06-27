package com.iishanto.kikhabo.infrastructure.prompt.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.infrastructure.prompt.PromptProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class GeminiPromptImpl implements PromptProvider {
    ObjectMapper objectMapper;
    Logger logger;

    @Override
    public String getPrompt(Prompt prompt) {
        String promptString = """
                {
                    "contents": [{
                        "parts":[
                            {
                                "text": "%s"
                            }
                        ]
                    }],
                    "generationConfig": {
                        "temperature": 2.0
                     }
                }
                """.formatted(getUserPrompt(prompt));
        logger.info(promptString);
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
                Spicy rating of the meals: %f,
                Sweetness rating of the meals: %f,
                Salt rating of the meals: %f,
                Budget rating out of 10: %f,
                Current season: %s,
                Country of origin: %s
                
                You must follow this JSON schema structure inorder to be successfully parsed by mobile client
                        {
                                            status:<response status, success|error>,
                                            message:<status message>,
                                            data:{
                                            totalMeals:<the number of meals to generate>,
                                                meals:[
                                                    {
                                                        mealName:<name of the meal>,
                                                        totalEnergy:<total energy in kilocalorie| in float>,
                                                        note:<if any disclaimer needed to be maintained>,
                                                        ingredients:<comma seperated list of ingredients>,
                                                        groceries:{
                                                            total:<total groceries to buy>,
                                                            itemList:[
                                                                {
                                                                    name:<name of the grocery item>,
                                                                    priceRatingOutOf10:<price rating>,
                                                                    amountInGm:<how much to buy>
                                                                },
                                                                <list of remaining groceries>
                                                            ]
                                                        },
                                                        <list of remaining meals>
                                                    }
                                                ]
                                            }
                                        }
                The json should be a valid json and strings and keys should be quoted. Also, randomize meals in each request. Consider the generator seed in this case. Also keep the note empty if not that necessary. 
                """.formatted(
                        System.currentTimeMillis(),
                        prompt.getMealPreferenceData().getTotalMealCount(),
                        4/*Number of total person*/,
                        "20,22,23,21" /*Age list of the persons*/,
                        "18,20,30,32" /*BMI list of the persons*/,
                        "10,5,10,7" /*Working hours list of the persons*/,
                        5.4 /*Spicy rating*/,
                        3.2 /*Sweetness rating*/,
                        7.0 /*Salt rating of the meals*/,
                        4.0 /*Budget rating*/,
                        "Winter" /*Season*/,
                        "Bangladesh" /*Country*/
                );
    }
}
