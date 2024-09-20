import baseURL from '../../config.js';
import axios from 'axios';

class search_family_datasource  {
    searchFamily=async(query)=>{
        try{
            const response =await axios.get(
                `${baseURL}/api/v1/user/search`,{ 
                    headers: {Authorization: `Bearer ${localStorage.getItem('token')}`},
                    params:{query:query}
            }
            );
            
            return response.data;
        }catch(error){
            console.log(error);
        }
    }
}
export default search_family_datasource;