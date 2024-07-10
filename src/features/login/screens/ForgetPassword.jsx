import React from 'react'
import { Link } from 'react-router-dom';


const ForgetPassword = () => {
  return (
    <div>
        <div className="bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">
            
            { /* <h1 className="text-4xl text-black font-bold text-center mb-6" >Login</h1> */}
             <BiUser className=' bg-green-400 rounded-full top-4 ml-32 h-10  w-10 hover:bg-pink-500 ' />
            <form action="">
            <div className="relative my-4 " >
                <input className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="email"/>
                <label className=" absolute text-xl text-black duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6 " htmlFor=''>Your Email</label>
            </div>
           
            
             <button className="w-full mb-4 text-[18px] mt-6 rounded-full bg-emerald-700 hover:bg-white py-2 transition-colors duration-300" type="submit">Send Reset Email</button>
              
             

            </form>

        </div>
    </div>
  );
};

export default ForgetPassword;
