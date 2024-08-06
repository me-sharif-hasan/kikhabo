import React from 'react';
import {AiOutlineLoading } from 'react-icons/ai';

const PrimaryButton = ({loading=false}) => {

  return (
    
      <button className="w-full flex justify-center mb-4 text-[18px] mt-6 rounded-full bg-green-500 hover:bg-white py-2 transition-colors duration-300" type="submit">
        {loading&&<AiOutlineLoading className=' left-16 top-3 animate-spin'/>}Login
        
        
      </button>
  )
};

export default PrimaryButton;
