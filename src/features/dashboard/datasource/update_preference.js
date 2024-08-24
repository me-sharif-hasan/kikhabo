import baseURL from '../../config';
import axios from 'axios';

class dashboard_datasource  {
    setPreference=async(spicyRating,budgetRating,SaltTasteRating,hasDiabetics,isPregnant,specialNotes)=>{
        try{
            const response =await axios.post(
                `${baseURL}/api/v1/meal-planning/update-preference`,
                { spicyRating,budgetRating,SaltTasteRating,hasDiabetics,isPregnant,specialNotes}, {
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