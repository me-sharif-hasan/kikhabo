import React from 'react';
import {AiOutlineLock} from "react-icons/ai";

const InputTextField = () => {
  return (
    <div>
      <div className="relative my-4 " >
                <input className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="email"/>
                <label className=" absolute text-xl text-black duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6 " htmlFor=''>Your Email</label>
            </div>
            <div className="relative my-4">
                <input className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="password"/>
                <label className="absolute text-xl text-black duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6 " htmlFor=''>Your Password</label>
                <AiOutlineLock className='absolute top-0 right-4' />
            </div>
    </div>
  )
}

export default InputTextField
