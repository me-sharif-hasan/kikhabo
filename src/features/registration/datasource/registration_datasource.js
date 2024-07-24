import baseURL from '../../config.js';
import axios from 'axios';

 class registration_datasource{
    doRegister=async(name)=>{
        try{
            const response =await axios.post(`${baseURL}/api/v1`, {name} ) ;
            return response.data;
        }catch(error){
            throw error.response.data;
        }

    }

}

export default registration_datasource;
