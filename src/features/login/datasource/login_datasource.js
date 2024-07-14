import baseURL from './config';
import axios from 'axios';

class login_datasource  {
    doLogin=async(email,password)=>{
        try{
            const response =await axios.post(`${baseURL}/api/v1/user/login`, { email, password });
            console.log(response);
            return response.data;
        }catch(error){
            console.error(error);
            throw error.response.data;
        }
    }
}

export default login_datasource;
