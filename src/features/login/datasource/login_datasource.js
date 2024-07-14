import  { Component } from 'react'
import baseURL from './config';
import axios from 'axios';

export class login_datasource extends Component {
    doLogin=async(email,password)=>{
        try{
            const response =await axios.post(`${baseURL}/api/v1/user/login`, { email, password });
            return response.data;
        }catch(error){
            throw error.response.data;
        }
    }
}

export default login_datasource
