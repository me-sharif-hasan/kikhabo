import baseURL from '../../config.js';
import axios from 'axios';

class family_datasource  {
    manageFamily=async()=>{
        try{
            const response =await axios.get(
                `${baseURL}/api/v1/family`,{ headers: {Authorization: `Bearer ${localStorage.getItem('token')}`} }
            );
            return response.data;
        }catch(error){
            console.log(error)
        }
    }
}
export default family_datasource;