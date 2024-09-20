import baseURL from '../../config.js';
import axios from 'axios';

class family_datasource  {
    Family=async()=>{
        try{
            const response =await axios.get(
                `${baseURL}/api/v1/family`,{ headers: {Authorization: `Bearer ${localStorage.getItem('token')}`} }
            );
            return response.data;
        }catch(error){
            console.log(error)
        }
    }

    deleteFamilyMember=async(id)=> {
        try {
            const response = await axios.delete(
                `${baseURL}/api/v1/family/${id}`, { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
            );
        } catch (error) {
            console.log(error);
        }
    }
}
export default family_datasource;