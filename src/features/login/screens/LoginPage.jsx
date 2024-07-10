import React from 'react'
import { Link } from 'react-router-dom';

const LoginPage = () => {
  return (
    <div className=" ">
        <div className="bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">
            
            <h1 className="text-4xl text-black font-bold text-center mb-6" >Login</h1>
            
            <form action="">
            <div className="relative my-4 " >
                <input className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="email"/>
                <label className="absolute text-sm text-black duration-300 transform-translate-y-6 scale-75 top-3-z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-6 " htmlFor=''>Your Email</label>
            </div>
            <div className="relative my-4">
                <input className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="password"/>
                <label className="absolute text-sm text-black duration-300 transform-translate-y-6 scale-75 top-3-z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-6 " htmlFor=''>Your Password</label>
            </div>
            <div>
                <div>
                <input  type="checkbox" name="" id="" />
                <label htmlFor='Remember Me'>Remember Me</label>
                </div>
                <span>Forget password ?</span>
             </div>
             <button type="submit">Login</button>
              
             <div>
              <span>New Here ? <Link to="Register">Create an account .</Link> </span>
              </div> 

            </form>

        </div>
    </div>
  );
};

export default LoginPage;
