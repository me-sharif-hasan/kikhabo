import React from 'react'
import { Link } from 'react-router-dom';
import {BiUser} from "react-icons/bi";

import PrimaryButton from '../widgets/PrimaryButton';
import InputTextField from '../widgets/InputTextField';

const LoginPage = () => {
  return (
    <div>
        <div className="bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">
            
            { /* <h1 className="text-4xl text-black font-bold text-center mb-6" >Login</h1> */}
             <BiUser className=' bg-green-400 rounded-full top-4 ml-32 h-10  w-10 hover:bg-pink-500 ' />
            <form action="">
            
            <InputTextField/>

            <div className="flex justify-between items-center " >
                <div className="flex gap-2 items-center  " >
                <input  type="checkbox" name="" id="" />
                <label htmlFor='Remember Me'>Remember Me</label>
                </div>
                <span className="text-pink-800 hover:text-green-900"  >Forget password ?</span>
             </div>
             <PrimaryButton/>
              
             <div>
              <span className="m-4" >New Here ? <Link className='text-pink-800 hover:text-green-900' to="Register">Create an account .</Link> </span>
              </div> 

            </form>

        </div>
    </div>
  );
};

export default LoginPage;
