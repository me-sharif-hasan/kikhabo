import React, { useState } from 'react'
import {BiUser} from "react-icons/bi";
import PrimaryButton from '../widgets/PrimaryButton';
import InputTextField from '../widgets/InputTextField';
import RememberMe from './RememberMe';
import { useNavigate } from 'react-router-dom';
import {AiOutlineLock} from "react-icons/ai";


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
    try {
      const response = await axios.post('https://kikhabo.onrender.com/api/v1/user/login', { email, password });
      if (response.data.success) {
        // Redirect to the Dashboard page
        navigate('/dashboard');
      } else {
        setError('Invalid email or password');
      }
    } catch (error) {
      setError('An error occurred. Please try again.');
    }
  };

  return (
    <div>

      <div className="text-black-900 bg-bgImage  w-full h-[100vh] flex justify-center items-center bg-no-repeat bg-center">

        <div className="bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">
            
            <BiUser className=' bg-green-400 rounded-full top-4 ml-32 h-10  w-10 hover:bg-pink-500 ' />
            <form onSubmit={handleSubmit}>  

            {/*<InputTextField/>*/}
            <div className="relative my-4 " >
                <input id="email" name="email" required value={email} onChange={handleEmailChange} className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="email"/>
                <label className=" absolute text-xl text-black duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6 " htmlFor=''>Your Email</label>
            </div>
            <div className="relative my-4">
                <input id="password" name="password" required value={password} onChange={handlePasswordChange}  className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="password" />
                <label className="absolute text-xl text-black duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6 " htmlFor=''>Your Password</label>
                <AiOutlineLock className='absolute top-0 right-4' />
            </div>
   




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
