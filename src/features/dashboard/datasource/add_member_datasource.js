import baseURL from '../../config.js';
import axios from 'axios';

const add_member_datasource = () => {
    addFamilyMember=async(ids)=>{
        try{
            const response =await axios.post(
                `${baseURL}/api/v1/family`,{ ids }, { headers: {Authorization: `Bearer ${localStorage.getItem('token')}`} }
            );
            return response.data;
        }catch(error){
            console.log(error)
        }
    }
}

export default add_member_datasource