import baseURL from '../../config';
import axios from 'axios';

class dashboard_datasource  {
    setPreference=async(spicyRating,priceRating,saltRating,hasDiabets,isPregnant,specialNotes)=>{
        try{
            const response =await axios.post(
                `${baseURL}/api/v1/meal-planning/update-preference`,
                { spicyRating,priceRating,saltRating,hasDiabets,isPregnant,specialNotes}, {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                }
            );
            return response.data;
        }catch(error){
            console.log(error)
        }
    }
}
export default dashboard_datasource;