package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.infrastructure.external.ai.ChatBotApi;
import com.iishanto.kikhabo.infrastructure.prompt.PromptProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ChatBotDataSourceImpl implements ChatBotDataSource {
    ChatBotApi chatBotApi;
    ObjectMapper objectMapper;
    PromptProvider promptProvider;
    Logger logger;
    @Override
    public String prompt(Prompt prompt) throws JsonProcessingException {
//        String bardResponse = chatBotApi.request(promptProvider.getPrompt(prompt));
//        logger.debug("Bard response: {}",bardResponse);
//        JsonNode bardJson = objectMapper.readTree(bardResponse);
//        JsonNode myOutput = bardJson.get("candidates").get(0).get("content").get("parts").get(0).get("text");
//        String geminiResponse=myOutput.asText();
//        logger.info("GEMINI Responded :- {}",geminiResponse);
//        String willBeOurServerResponse=geminiResponse.substring(geminiResponse.indexOf("{"),geminiResponse.lastIndexOf("}")+1);
//        return willBeOurServerResponse;
        return convertResponse("geminiResponse");
    }

    private String convertResponse(String geminiResponse) throws JsonProcessingException {
//        String willBeOurServerResponse=geminiResponse.substring(geminiResponse.indexOf("{"),geminiResponse.lastIndexOf("}")+1);
        String willBeOurServerResponse= """
                {
                "status": "success",
                "message": "Successfully generated 20 meal suggestions with grocery list.",
                "data": {
                "totalMeals": 20,
                "meals": [
                {
                "mealName": "Spicy Chicken Curry with Jeera Rice",
                "totalEnergy": 650.00,
                "note": "Adjust spice level to taste.",
                "ingredients": "Chicken, Onion, Garlic, Ginger, Tomato, Green Chili, Turmeric Powder, Red Chili Powder, Cumin Powder, Coriander Powder, Garam Masala, Oil, Rice, Cumin Seeds",
                "groceries": {
                "total": 8,
                "itemList": [
                {
                "name": "Chicken",
                "priceRatingOutOf10": 6,
                "amountInGm": 500
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 200
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 250
                },
                {
                "name": "Rice",
                "priceRatingOutOf10": 3,
                "amountInGm": 500
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Green Chili",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                "priceRatingOutOf10": 4,
                "amountInGm": 50
                },
                {
                "name": "Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 100
                }
                ]
                }
                },
                {
                "mealName": "Lentil Soup (Masoor Dal) with Rice",
                "totalEnergy": 450.00,
                "note": "Serve with a side of lemon wedges.",
                "ingredients": "Red Lentils, Onion, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Cumin Seeds, Mustard Seeds, Bay Leaf, Ghee or Oil, Salt, Coriander Leaves, Rice",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Red Lentils",
                "priceRatingOutOf10": 4,
                "amountInGm": 200
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Rice",
                "priceRatingOutOf10": 3,
                "amountInGm": 200
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Cumin Seeds, Mustard Seeds, Bay Leaf",
                "priceRatingOutOf10": 3,
                "amountInGm": 20
                },
                {
                "name": "Ghee/Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 50
                },
                {
                "name": "Coriander Leaves",
                "priceRatingOutOf10": 1,
                "amountInGm": 50
                }
                ]
                }
                },
                {
                "mealName": "Fish Curry with Steamed Rice",
                "totalEnergy": 550.00,
                "note": "Use any firm white fish like Rohu or Katla.",
                "ingredients": "Fish, Onion, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Mustard Oil, Green Chili, Tomato, Salt, Rice",
                "groceries": {
                "total": 8,
                "itemList": [
                {
                "name": "Fish",
                "priceRatingOutOf10": 7,
                "amountInGm": 500
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 150
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Rice",
                "priceRatingOutOf10": 3,
                "amountInGm": 500
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Green Chili",
                "priceRatingOutOf10": 2,
                "amountInGm": 30
                },
                {
                "name": "Spices (Turmeric, Chili, Cumin, Coriander)",
                "priceRatingOutOf10": 4,
                "amountInGm": 40
                },
                {
                "name": "Mustard Oil",
                "priceRatingOutOf10": 5,
                "amountInGm": 80
                }
                ]
                }
                },
                {
                "mealName": "Vegetable Biryani",
                "totalEnergy": 600.00,
                "note": "Add vegetables of your choice like cauliflower, carrots, peas.",
                "ingredients": "Basmati Rice, Mixed Vegetables, Onion, Garlic, Ginger, Yogurt, Biryani Masala, Ghee or Oil, Salt, Mint Leaves, Coriander Leaves, Saffron (optional)",
                "groceries": {
                "total": 8,
                "itemList": [
                {
                "name": "Basmati Rice",
                "priceRatingOutOf10": 5,
                "amountInGm": 400
                },
                {
                "name": "Mixed Vegetables",
                "priceRatingOutOf10": 4,
                "amountInGm": 500
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 150
                },
                {
                "name": "Yogurt",
                "priceRatingOutOf10": 3,
                "amountInGm": 100
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Biryani Masala",
                "priceRatingOutOf10": 4,
                "amountInGm": 30
                },
                {
                "name": "Ghee/Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 80
                },
                {
                "name": "Mint & Coriander Leaves",
                "priceRatingOutOf10": 1,
                "amountInGm": 50
                }
                ]
                }
                },
                {
                "mealName": "Chicken Fried Rice",
                "totalEnergy": 680.00,
                "note": "Quick and easy weeknight meal.",
                "ingredients": "Cooked Rice, Chicken, Onion, Garlic, Ginger, Soy Sauce, Oyster Sauce (optional), Green Chili, Salt, Vegetables (Carrots, Peas, Beans), Egg",
                "groceries": {
                "total": 8,
                "itemList": [
                {
                "name": "Cooked Rice",
                "priceRatingOutOf10": 3,
                "amountInGm": 400
                },
                {
                "name": "Chicken",
                "priceRatingOutOf10": 6,
                "amountInGm": 200
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Mixed Vegetables (Carrots, Peas, Beans)",
                "priceRatingOutOf10": 3,
                "amountInGm": 200
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 30
                },
                {
                "name": "Soy Sauce",
                "priceRatingOutOf10": 4,
                "amountInGm": 50
                },
                {
                "name": "Green Chili",
                "priceRatingOutOf10": 2,
                "amountInGm": 20
                },
                {
                "name": "Eggs",
                "priceRatingOutOf10": 5,
                "amountInGm": 4
                }
                ]
                }
                },
                {
                "mealName": "Aloo Gobi (Potato and Cauliflower Curry)",
                "totalEnergy": 350.00,
                "note": "Serve with roti or rice.",
                "ingredients": "Potato, Cauliflower, Onion, Garlic, Ginger, Tomato, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Potato",
                "priceRatingOutOf10": 2,
                "amountInGm": 500
                },
                {
                "name": "Cauliflower",
                "priceRatingOutOf10": 4,
                "amountInGm": 400
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 150
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                "priceRatingOutOf10": 4,
                "amountInGm": 40
                },
                {
                "name": "Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 60
                }
                ]
                }
                },
                {
                "mealName": "Egg Curry with Rice",
                "totalEnergy": 500.00,
                "note": "Adjust the consistency of the gravy as per your liking.",
                "ingredients": "Eggs, Onion, Tomato, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt, Rice",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Eggs",
                "priceRatingOutOf10": 5,
                "amountInGm": 6
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 150
                },
                {
                "name": "Rice",
                "priceRatingOutOf10": 3,
                "amountInGm": 500
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 40
                },
                {
                "name": "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                "priceRatingOutOf10": 4,
                "amountInGm": 30
                },
                {
                "name": "Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 60
                }
                ]
                }
                },
                {
                "mealName": "Chicken Salad",
                "totalEnergy": 400.00,
                "note": "Light and refreshing for summer.",
                "ingredients": "Chicken Breast, Lettuce, Cucumber, Tomato, Onion, Lemon, Olive Oil, Salt, Pepper",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Chicken Breast",
                "priceRatingOutOf10": 7,
                "amountInGm": 300
                },
                {
                "name": "Lettuce",
                "priceRatingOutOf10": 3,
                "amountInGm": 100
                },
                {
                "name": "Cucumber",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Lemon",
                "priceRatingOutOf10": 2,
                "amountInGm": 2
                },
                {
                "name": "Olive Oil",
                "priceRatingOutOf10": 6,
                "amountInGm": 30
                }
                ]
                }
                },
                {
                "mealName": "Vegetable Stir-Fry with Noodles",
                "totalEnergy": 550.00,
                "note": "Use your favourite vegetables.",
                "ingredients": "Noodles, Assorted Vegetables, Soy Sauce, Oyster Sauce, Garlic, Ginger, Chili Flakes, Oil, Salt",
                "groceries": {
                "total": 6,
                "itemList": [
                {
                "name": "Noodles",
                "priceRatingOutOf10": 4,
                "amountInGm": 400
                },
                {
                "name": "Assorted Vegetables",
                "priceRatingOutOf10": 4,
                "amountInGm": 400
                },
                {
                "name": "Garlic",
                "priceRatingOutOf10": 2,
                "amountInGm": 30
                },
                {
                "name": "Ginger",
                "priceRatingOutOf10": 2,
                "amountInGm": 20
                },
                {
                "name": "Soy Sauce",
                "priceRatingOutOf10": 4,
                "amountInGm": 40
                },
                {
                "name": "Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 50
                }
                ]
                }
                },
                {
                "mealName": "Chicken Wraps",
                "totalEnergy": 600.00,
                "note": "Quick and easy lunch or dinner option.",
                "ingredients": "Tortillas/Roti, Cooked Chicken, Lettuce, Tomato, Onion, Cucumber, Yogurt, Mayonnaise, Chili Sauce",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Tortillas/Roti",
                "priceRatingOutOf10": 4,
                "amountInGm": 400
                },
                {
                "name": "Cooked Chicken",
                "priceRatingOutOf10": 6,
                "amountInGm": 300
                },
                {
                "name": "Lettuce",
                "priceRatingOutOf10": 3,
                "amountInGm": 100
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Cucumber",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Yogurt/Mayonnaise",
                "priceRatingOutOf10": 3,
                "amountInGm": 100
                }
                ]
                }
                },
                {
                "mealName": "Vegetable Omelette with Toast",
                "totalEnergy": 450.00,
                "note": "Healthy and filling breakfast or brunch option.",
                "ingredients": "Eggs, Onion, Tomato, Green Chili, Bell Peppers (optional), Spinach (optional), Bread, Butter, Salt, Pepper",
                "groceries": {
                "total": 6,
                "itemList": [
                {
                "name": "Eggs",
                "priceRatingOutOf10": 5,
                "amountInGm": 8
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Green Chili",
                "priceRatingOutOf10": 2,
                "amountInGm": 20
                },
                {
                "name": "Bread",
                "priceRatingOutOf10": 3,
                "amountInGm": 200
                },
                {
                "name": "Butter",
                "priceRatingOutOf10": 4,
                "amountInGm": 50
                }
                ]
                }
                },
                {
                "mealName": "Chicken Noodle Soup",
                "totalEnergy": 400.00,
                "note": "Soothing and flavourful.",
                "ingredients": "Chicken Broth, Noodles, Cooked Chicken, Carrot, Celery, Onion, Garlic, Ginger, Soy Sauce, Salt, Pepper",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Chicken Broth",
                "priceRatingOutOf10": 4,
                "amountInGm": 500
                },
                {
                "name": "Noodles",
                "priceRatingOutOf10": 4,
                "amountInGm": 200
                },
                {
                "name": "Cooked Chicken",
                "priceRatingOutOf10": 6,
                "amountInGm": 200
                },
                {
                "name": "Carrot",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Garlic",
                "priceRatingOutOf10": 2,
                "amountInGm": 20
                },
                {
                "name": "Soy Sauce",
                "priceRatingOutOf10": 4,
                "amountInGm": 30
                }
                ]
                }
                },
                {
                "mealName": "Lentil and Vegetable Soup (Mix Dal)",
                "totalEnergy": 380.00,
                "note": "A hearty and healthy soup.",
                "ingredients": "Yellow Lentils, Red Lentils, Green Lentils, Spinach, Carrot, Tomato, Onion, Garlic, Ginger, Turmeric Powder, Cumin Seeds, Coriander Powder, Oil, Salt",
                "groceries": {
                "total": 8,
                "itemList": [
                {
                "name": "Yellow Lentils",
                "priceRatingOutOf10": 4,
                "amountInGm": 100
                },
                {
                "name": "Red Lentils",
                "priceRatingOutOf10": 4,
                "amountInGm": 50
                },
                {
                "name": "Green Lentils",
                "priceRatingOutOf10": 5,
                "amountInGm": 50
                },
                {
                "name": "Spinach",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Carrot",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Onion, Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 150
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 40
                },
                {
                "name": "Spices (Turmeric, Cumin, Coriander)",
                "priceRatingOutOf10": 4,
                "amountInGm": 30
                }
                ]
                }
                },
                {
                "mealName": "Chickpea Curry (Chana Masala)",
                "totalEnergy": 420.00,
                "note": "Serve with rice or roti.",
                "ingredients": "Chickpeas, Onion, Tomato, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt, Chopped Cilantro",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Chickpeas (Kabuli Chana)",
                "priceRatingOutOf10": 5,
                "amountInGm": 400
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 150
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                "priceRatingOutOf10": 4,
                "amountInGm": 40
                },
                {
                "name": "Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 60
                },
                {
                "name": "Chopped Cilantro",
                "priceRatingOutOf10": 1,
                "amountInGm": 50
                }
                ]
                }
                },
                {
                "mealName": "Potato and Pea Curry (Aloo Matar)",
                "totalEnergy": 390.00,
                "note": "A classic and comforting curry.",
                "ingredients": "Potato, Green Peas, Onion, Tomato, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt, Fresh Coriander Leaves",
                "groceries": {
                "total": 7,
                "itemList": [
                {
                "name": "Potato",
                "priceRatingOutOf10": 2,
                "amountInGm": 500
                },
                {
                "name": "Green Peas",
                "priceRatingOutOf10": 3,
                "amountInGm": 200
                },
                {
                "name": "Onion",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Tomato",
                "priceRatingOutOf10": 2,
                "amountInGm": 150
                },
                {
                "name": "Ginger-Garlic Paste",
                "priceRatingOutOf10": 2,
                "amountInGm": 50
                },
                {
                "name": "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                "priceRatingOutOf10": 4,
                "amountInGm": 40
                },
                {
                "name": "Oil",
                "priceRatingOutOf10": 4,
                "amountInGm": 60
                }
                ]
                }
                },
                {
                "mealName": "Sautéed Spinach with Garlic",
                "totalEnergy": 250.00,
                "note": "A simple and healthy side dish.",
                "ingredients": "Spinach, Garlic, Olive Oil, Salt, Pepper",
                "groceries": {
                "total": 4,
                "itemList": [
                {
                "name": "Spinach",
                "priceRatingOutOf10": 2,
                "amountInGm": 300
                },
                {
                "name": "Garlic",
                "priceRatingOutOf10": 2,
                "amountInGm": 30
                },
                {
                "name": "Olive Oil",
                "priceRatingOutOf10": 6,
                "amountInGm": 30
                },
                {
                "name": "Salt & Pepper",
                "priceRatingOutOf10": 2,
                "amountInGm": 10
                }
                ]
                }
                },
                {
                "mealName": "Cucumber Raita",
                "totalEnergy": 150.00,
                "note": "A refreshing yogurt dip or side dish.",
                "ingredients": "Yogurt, Cucumber, Green Chili, Salt, Cumin Powder",
                "groceries": {
                "total": 4,
                "itemList": [
                {
                "name": "Yogurt",
                "priceRatingOutOf10": 3,
                "amountInGm": 250
                },
                {
                "name": "Cucumber",
                "priceRatingOutOf10": 2,
                "amountInGm": 100
                },
                {
                "name": "Green Chili",
                "priceRatingOutOf10": 2,
                "amountInGm": 10
                },
                {
                "name": "Cumin Powder",
                "priceRatingOutOf10": 3,
                "amountInGm": 10
                }
                ]
                }
                },
                {
                "mealName": "Mashed Potatoes",
                "totalEnergy": 300.00,
                "note": "A comforting and versatile side dish.",
                "ingredients": "Potatoes, Butter, Milk, Salt, Pepper",
                "groceries": {
                "total": 4,
                "itemList": [
                {
                "name": "Potatoes",
                "priceRatingOutOf10": 2,
                "amountInGm": 500
                },
                {
                "name": "Butter",
                "priceRatingOutOf10": 4,
                "amountInGm": 50
                },
                {
                "name": "Milk",
                "priceRatingOutOf10": 3,
                "amountInGm": 100
                },
                {
                "name": "Salt & Pepper",
                "priceRatingOutOf10": 2,
                "amountInGm": 10
                }
                ]
                }
                },
                {
                "mealName": "Fruit Salad",
                "totalEnergy": 200.00,
                "note": "A refreshing and healthy dessert or snack.",
                "ingredients": "Assorted Fruits (Banana, Apple, Grapes, Orange)",
                "groceries": {
                "total": 4,
                "itemList": [
                {
                "name": "Banana",
                "priceRatingOutOf10": 2,
                "amountInGm": 200
                },
                {
                "name": "Apple",
                "priceRatingOutOf10": 4,
                "amountInGm": 200
                },
                {
                "name": "Grapes",
                "priceRatingOutOf10": 3,
                "amountInGm": 200
                },
                {
                "name": "Orange",
                "priceRatingOutOf10": 3,
                "amountInGm": 200
                }
                ]
                }
                }
                ]
                }
                }
                ```
                2024-06-26T23:32:14.739+06:00  INFO 6448 --- [kikhabo] [nio-8080-exec-1] ii system log                            : {
                  "status" : "success",
                  "message" : "Successfully generated 20 meal suggestions with grocery list.",
                  "data" : {
                    "totalMeals" : 20,
                    "meals" : [ {
                      "mealName" : "Spicy Chicken Curry with Jeera Rice",
                      "totalEnergy" : 650.0,
                      "note" : "Adjust spice level to taste.",
                      "ingredients" : "Chicken, Onion, Garlic, Ginger, Tomato, Green Chili, Turmeric Powder, Red Chili Powder, Cumin Powder, Coriander Powder, Garam Masala, Oil, Rice, Cumin Seeds",
                      "groceries" : {
                        "total" : 8,
                        "itemList" : [ {
                          "name" : "Chicken",
                          "priceRatingOutOf10" : 6,
                          "amountInGm" : 500
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 200
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 250
                        }, {
                          "name" : "Rice",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 500
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Green Chili",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 50
                        }, {
                          "name" : "Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 100
                        } ]
                      }
                    }, {
                      "mealName" : "Lentil Soup (Masoor Dal) with Rice",
                      "totalEnergy" : 450.0,
                      "note" : "Serve with a side of lemon wedges.",
                      "ingredients" : "Red Lentils, Onion, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Cumin Seeds, Mustard Seeds, Bay Leaf, Ghee or Oil, Salt, Coriander Leaves, Rice",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Red Lentils",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 200
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Rice",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 200
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Cumin Seeds, Mustard Seeds, Bay Leaf",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 20
                        }, {
                          "name" : "Ghee/Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 50
                        }, {
                          "name" : "Coriander Leaves",
                          "priceRatingOutOf10" : 1,
                          "amountInGm" : 50
                        } ]
                      }
                    }, {
                      "mealName" : "Fish Curry with Steamed Rice",
                      "totalEnergy" : 550.0,
                      "note" : "Use any firm white fish like Rohu or Katla.",
                      "ingredients" : "Fish, Onion, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Mustard Oil, Green Chili, Tomato, Salt, Rice",
                      "groceries" : {
                        "total" : 8,
                        "itemList" : [ {
                          "name" : "Fish",
                          "priceRatingOutOf10" : 7,
                          "amountInGm" : 500
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 150
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Rice",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 500
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Green Chili",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 30
                        }, {
                          "name" : "Spices (Turmeric, Chili, Cumin, Coriander)",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 40
                        }, {
                          "name" : "Mustard Oil",
                          "priceRatingOutOf10" : 5,
                          "amountInGm" : 80
                        } ]
                      }
                    }, {
                      "mealName" : "Vegetable Biryani",
                      "totalEnergy" : 600.0,
                      "note" : "Add vegetables of your choice like cauliflower, carrots, peas.",
                      "ingredients" : "Basmati Rice, Mixed Vegetables, Onion, Garlic, Ginger, Yogurt, Biryani Masala, Ghee or Oil, Salt, Mint Leaves, Coriander Leaves, Saffron (optional)",
                      "groceries" : {
                        "total" : 8,
                        "itemList" : [ {
                          "name" : "Basmati Rice",
                          "priceRatingOutOf10" : 5,
                          "amountInGm" : 400
                        }, {
                          "name" : "Mixed Vegetables",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 500
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 150
                        }, {
                          "name" : "Yogurt",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 100
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Biryani Masala",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 30
                        }, {
                          "name" : "Ghee/Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 80
                        }, {
                          "name" : "Mint & Coriander Leaves",
                          "priceRatingOutOf10" : 1,
                          "amountInGm" : 50
                        } ]
                      }
                    }, {
                      "mealName" : "Chicken Fried Rice",
                      "totalEnergy" : 680.0,
                      "note" : "Quick and easy weeknight meal.",
                      "ingredients" : "Cooked Rice, Chicken, Onion, Garlic, Ginger, Soy Sauce, Oyster Sauce (optional), Green Chili, Salt, Vegetables (Carrots, Peas, Beans), Egg",
                      "groceries" : {
                        "total" : 8,
                        "itemList" : [ {
                          "name" : "Cooked Rice",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 400
                        }, {
                          "name" : "Chicken",
                          "priceRatingOutOf10" : 6,
                          "amountInGm" : 200
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Mixed Vegetables (Carrots, Peas, Beans)",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 200
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 30
                        }, {
                          "name" : "Soy Sauce",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 50
                        }, {
                          "name" : "Green Chili",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 20
                        }, {
                          "name" : "Eggs",
                          "priceRatingOutOf10" : 5,
                          "amountInGm" : 4
                        } ]
                      }
                    }, {
                      "mealName" : "Aloo Gobi (Potato and Cauliflower Curry)",
                      "totalEnergy" : 350.0,
                      "note" : "Serve with roti or rice.",
                      "ingredients" : "Potato, Cauliflower, Onion, Garlic, Ginger, Tomato, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Potato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 500
                        }, {
                          "name" : "Cauliflower",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 400
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 150
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 40
                        }, {
                          "name" : "Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 60
                        } ]
                      }
                    }, {
                      "mealName" : "Egg Curry with Rice",
                      "totalEnergy" : 500.0,
                      "note" : "Adjust the consistency of the gravy as per your liking.",
                      "ingredients" : "Eggs, Onion, Tomato, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt, Rice",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Eggs",
                          "priceRatingOutOf10" : 5,
                          "amountInGm" : 6
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 150
                        }, {
                          "name" : "Rice",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 500
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 40
                        }, {
                          "name" : "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 30
                        }, {
                          "name" : "Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 60
                        } ]
                      }
                    }, {
                      "mealName" : "Chicken Salad",
                      "totalEnergy" : 400.0,
                      "note" : "Light and refreshing for summer.",
                      "ingredients" : "Chicken Breast, Lettuce, Cucumber, Tomato, Onion, Lemon, Olive Oil, Salt, Pepper",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Chicken Breast",
                          "priceRatingOutOf10" : 7,
                          "amountInGm" : 300
                        }, {
                          "name" : "Lettuce",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 100
                        }, {
                          "name" : "Cucumber",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Lemon",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 2
                        }, {
                          "name" : "Olive Oil",
                          "priceRatingOutOf10" : 6,
                          "amountInGm" : 30
                        } ]
                      }
                    }, {
                      "mealName" : "Vegetable Stir-Fry with Noodles",
                      "totalEnergy" : 550.0,
                      "note" : "Use your favourite vegetables.",
                      "ingredients" : "Noodles, Assorted Vegetables, Soy Sauce, Oyster Sauce, Garlic, Ginger, Chili Flakes, Oil, Salt",
                      "groceries" : {
                        "total" : 6,
                        "itemList" : [ {
                          "name" : "Noodles",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 400
                        }, {
                          "name" : "Assorted Vegetables",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 400
                        }, {
                          "name" : "Garlic",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 30
                        }, {
                          "name" : "Ginger",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 20
                        }, {
                          "name" : "Soy Sauce",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 40
                        }, {
                          "name" : "Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 50
                        } ]
                      }
                    }, {
                      "mealName" : "Chicken Wraps",
                      "totalEnergy" : 600.0,
                      "note" : "Quick and easy lunch or dinner option.",
                      "ingredients" : "Tortillas/Roti, Cooked Chicken, Lettuce, Tomato, Onion, Cucumber, Yogurt, Mayonnaise, Chili Sauce",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Tortillas/Roti",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 400
                        }, {
                          "name" : "Cooked Chicken",
                          "priceRatingOutOf10" : 6,
                          "amountInGm" : 300
                        }, {
                          "name" : "Lettuce",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 100
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Cucumber",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Yogurt/Mayonnaise",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 100
                        } ]
                      }
                    }, {
                      "mealName" : "Vegetable Omelette with Toast",
                      "totalEnergy" : 450.0,
                      "note" : "Healthy and filling breakfast or brunch option.",
                      "ingredients" : "Eggs, Onion, Tomato, Green Chili, Bell Peppers (optional), Spinach (optional), Bread, Butter, Salt, Pepper",
                      "groceries" : {
                        "total" : 6,
                        "itemList" : [ {
                          "name" : "Eggs",
                          "priceRatingOutOf10" : 5,
                          "amountInGm" : 8
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Green Chili",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 20
                        }, {
                          "name" : "Bread",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 200
                        }, {
                          "name" : "Butter",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 50
                        } ]
                      }
                    }, {
                      "mealName" : "Chicken Noodle Soup",
                      "totalEnergy" : 400.0,
                      "note" : "Soothing and flavourful.",
                      "ingredients" : "Chicken Broth, Noodles, Cooked Chicken, Carrot, Celery, Onion, Garlic, Ginger, Soy Sauce, Salt, Pepper",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Chicken Broth",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 500
                        }, {
                          "name" : "Noodles",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 200
                        }, {
                          "name" : "Cooked Chicken",
                          "priceRatingOutOf10" : 6,
                          "amountInGm" : 200
                        }, {
                          "name" : "Carrot",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Garlic",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 20
                        }, {
                          "name" : "Soy Sauce",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 30
                        } ]
                      }
                    }, {
                      "mealName" : "Lentil and Vegetable Soup (Mix Dal)",
                      "totalEnergy" : 380.0,
                      "note" : "A hearty and healthy soup.",
                      "ingredients" : "Yellow Lentils, Red Lentils, Green Lentils, Spinach, Carrot, Tomato, Onion, Garlic, Ginger, Turmeric Powder, Cumin Seeds, Coriander Powder, Oil, Salt",
                      "groceries" : {
                        "total" : 8,
                        "itemList" : [ {
                          "name" : "Yellow Lentils",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 100
                        }, {
                          "name" : "Red Lentils",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 50
                        }, {
                          "name" : "Green Lentils",
                          "priceRatingOutOf10" : 5,
                          "amountInGm" : 50
                        }, {
                          "name" : "Spinach",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Carrot",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Onion, Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 150
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 40
                        }, {
                          "name" : "Spices (Turmeric, Cumin, Coriander)",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 30
                        } ]
                      }
                    }, {
                      "mealName" : "Chickpea Curry (Chana Masala)",
                      "totalEnergy" : 420.0,
                      "note" : "Serve with rice or roti.",
                      "ingredients" : "Chickpeas, Onion, Tomato, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt, Chopped Cilantro",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Chickpeas (Kabuli Chana)",
                          "priceRatingOutOf10" : 5,
                          "amountInGm" : 400
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 150
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 40
                        }, {
                          "name" : "Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 60
                        }, {
                          "name" : "Chopped Cilantro",
                          "priceRatingOutOf10" : 1,
                          "amountInGm" : 50
                        } ]
                      }
                    }, {
                      "mealName" : "Potato and Pea Curry (Aloo Matar)",
                      "totalEnergy" : 390.0,
                      "note" : "A classic and comforting curry.",
                      "ingredients" : "Potato, Green Peas, Onion, Tomato, Garlic, Ginger, Turmeric Powder, Red Chili Powder, Coriander Powder, Cumin Powder, Garam Masala, Oil, Salt, Fresh Coriander Leaves",
                      "groceries" : {
                        "total" : 7,
                        "itemList" : [ {
                          "name" : "Potato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 500
                        }, {
                          "name" : "Green Peas",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 200
                        }, {
                          "name" : "Onion",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Tomato",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 150
                        }, {
                          "name" : "Ginger-Garlic Paste",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 50
                        }, {
                          "name" : "Spices (Turmeric, Chili, Cumin, Coriander, Garam Masala)",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 40
                        }, {
                          "name" : "Oil",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 60
                        } ]
                      }
                    }, {
                      "mealName" : "Sautéed Spinach with Garlic",
                      "totalEnergy" : 250.0,
                      "note" : "A simple and healthy side dish.",
                      "ingredients" : "Spinach, Garlic, Olive Oil, Salt, Pepper",
                      "groceries" : {
                        "total" : 4,
                        "itemList" : [ {
                          "name" : "Spinach",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 300
                        }, {
                          "name" : "Garlic",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 30
                        }, {
                          "name" : "Olive Oil",
                          "priceRatingOutOf10" : 6,
                          "amountInGm" : 30
                        }, {
                          "name" : "Salt & Pepper",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 10
                        } ]
                      }
                    }, {
                      "mealName" : "Cucumber Raita",
                      "totalEnergy" : 150.0,
                      "note" : "A refreshing yogurt dip or side dish.",
                      "ingredients" : "Yogurt, Cucumber, Green Chili, Salt, Cumin Powder",
                      "groceries" : {
                        "total" : 4,
                        "itemList" : [ {
                          "name" : "Yogurt",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 250
                        }, {
                          "name" : "Cucumber",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 100
                        }, {
                          "name" : "Green Chili",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 10
                        }, {
                          "name" : "Cumin Powder",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 10
                        } ]
                      }
                    }, {
                      "mealName" : "Mashed Potatoes",
                      "totalEnergy" : 300.0,
                      "note" : "A comforting and versatile side dish.",
                      "ingredients" : "Potatoes, Butter, Milk, Salt, Pepper",
                      "groceries" : {
                        "total" : 4,
                        "itemList" : [ {
                          "name" : "Potatoes",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 500
                        }, {
                          "name" : "Butter",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 50
                        }, {
                          "name" : "Milk",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 100
                        }, {
                          "name" : "Salt & Pepper",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 10
                        } ]
                      }
                    }, {
                      "mealName" : "Fruit Salad",
                      "totalEnergy" : 200.0,
                      "note" : "A refreshing and healthy dessert or snack.",
                      "ingredients" : "Assorted Fruits (Banana, Apple, Grapes, Orange)",
                      "groceries" : {
                        "total" : 4,
                        "itemList" : [ {
                          "name" : "Banana",
                          "priceRatingOutOf10" : 2,
                          "amountInGm" : 200
                        }, {
                          "name" : "Apple",
                          "priceRatingOutOf10" : 4,
                          "amountInGm" : 200
                        }, {
                          "name" : "Grapes",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 200
                        }, {
                          "name" : "Orange",
                          "priceRatingOutOf10" : 3,
                          "amountInGm" : 200
                        } ]
                      }
                    } ]
                  }
                }
                """;
        return willBeOurServerResponse;
    }
}
