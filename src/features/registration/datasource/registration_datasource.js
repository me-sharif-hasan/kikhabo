import baseURL from '../../config.js';
import axios from 'axios';

 class registration_datasource{
    doRegister=async(id,email,password,firstName,lastName,country,gender,dateOfBirth,weightInKg, heightInKg)=>{
        try{
            const response =await axios.post(`${baseURL}/api/v1/user/register`, {id,email,password,firstName,lastName,country,gender,dateOfBirth,weightInKg, heightInKg} ) ;
            return response.data;
        }catch(error){
            throw error.response.data;
        }

    }

}

export default registration_datasource;
