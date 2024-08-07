import baseURL from '../../config.js';
import axios from 'axios';

class login_datasource  {
    doLogin=async(email,password)=>{
        try{
            const response =await axios.post(`${baseURL}/api/v1/user/login`, { email, password }
            );
            localStorage.setItem('token', response.data.token);
            return response.data;
        }catch(error){
          
            throw error.response.data;
        }
    }
}

export default login_datasource;