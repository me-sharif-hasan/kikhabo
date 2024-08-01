import baseURL from '../../config.js';
import axios from 'axios';

class dashboard_datasource  {
    mealRequest=async(spicyRating,saltRating,dayCount,priceRating,totalMealCount,agesOfTheMembers)=>{
        try{
           // const token = localStorage.getItem('token');
            let ages=agesOfTheMembers?.split(',')??[24];
            const response =await axios.post(
                `${baseURL}/api/v1/meal-planning`,
                { spicyRating,saltRating,dayCount,priceRating,totalMealCount,ages }, {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                }
            );
            return response.data;
        }catch(error){
            // throw error.response.data;
            console.log(error)
        }
    }
}
export default dashboard_datasource;