import baseURL from '../../config.js';
import axios from 'axios';

class current_user_datasource  {
    currentUser=async()=>{
        try{ 
            const response =await axios.post(
                `${baseURL}/api/v1/user/current-user`,
                 {
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
export default current_user_datasource;