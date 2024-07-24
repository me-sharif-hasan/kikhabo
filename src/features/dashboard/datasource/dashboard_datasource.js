import baseURL from './config.js';
import axios from 'axios';

class dashboard_datasource  {
    mealRequest=async(spicyRating,saltRating,dayCount,priceRating,totalMealCount,agesOfTheMembers)=>{
        try{
            const response =await axios.post(`${baseURL}/api/v1/meal-planning`, { spicyRating,saltRating,dayCount,priceRating,totalMealCount,agesOfTheMembers });
            
            return response.data;
        }catch(error){
          
            throw error.response.data;
        }
    }
}

export default dashboard_datasource;
