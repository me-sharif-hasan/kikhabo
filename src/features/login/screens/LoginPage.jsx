import React, { useState } from 'react'
import {BiUser} from "react-icons/bi";
import PrimaryButton from '../widgets/PrimaryButton';
import InputTextField from '../widgets/InputTextField';
import RememberMe from './RememberMe';
import { useNavigate } from 'react-router-dom';
import {AiOutlineLock} from "react-icons/ai";
const navigate = useNavigate();



const LoginPage = () => {
  const [email, setEmail]=useState('');
  const [password, setPassword]=useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    login_datasource.doLogin(email,password).then((response)=>{
      
      console.log(response);

      if (response.data.status=='success') {
        // Redirect to the Dashboard page
        navigate('/dashboard');
      }
    }).catch((error)=>{
      setError(error.data.message);
    })
     
  };

  return (
    <div>

      <div className="text-black-900 bg-bgImage  w-full h-[100vh] flex justify-center items-center bg-no-repeat bg-center">

        <div className="bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">
            
            <BiUser className=' bg-green-400 rounded-full top-4 ml-32 h-10  w-10 hover:bg-pink-500 ' />
            <form onSubmit={handleSubmit}>  

            <InputTextField/>

            <RememberMe/>                          
            <PrimaryButton/>
            {error && <p className="error">{error}</p>}
            </form>

        </div>
    </div>
    </div>
  );
};

export default LoginPage;
