import React, { useState } from 'react'
import {BiUser} from "react-icons/bi";
import PrimaryButton from '../widgets/PrimaryButton';
import InputTextField from '../widgets/InputTextField';
import RememberMe from './RememberMe';
import { useNavigate } from 'react-router-dom';
import login_datasource from '../datasource/login_datasource';

const LoginPage = () => {
  let login= new login_datasource();
  
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const handleSubmit = async (e) => {
    e.preventDefault();
    let email=e.target[0].value;
    let password=e.target[1].value;
    setLoading(true);
    login.doLogin(email,password).then((response)=>{      
      console.log(response);
      if (response.status=='success') {
        setLoading(false);
        navigate('/dashboard');
      }
    }).catch((error)=>{
      console.log(error);
      setError(error.message);
    })     
  };

  return (
    <div>
      <div className="text-black-900 bg-bgImage  w-full h-[100vh] flex justify-center items-center bg-no-repeat bg-center">
        <div className="bg-green-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-30 relative">            
            <BiUser className=' bg-green-400 rounded-full top-4 ml-32 h-10  w-10 hover:bg-pink-500 ' />
            <form onSubmit={handleSubmit}>  
            <InputTextField/>
            <RememberMe/>
            <PrimaryButton loading={loading} />
            <p className="error">{error}</p>
            </form>
        </div>
    </div>
    </div>
  );
};

export default LoginPage;
