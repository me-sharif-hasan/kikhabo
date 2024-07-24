import React from 'react';
import InputTextField from '../widgets/InputTextField.jsx';
import PrimaryButton from  '../widgets/PrimaryButton.jsx';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

const Registration = () => {

    const [error, setError] = useState('');
  const navigate = useNavigate();


  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Submitted");
     
  };

  return (
    <div>

    <div className=" mt-24 bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">        
    <form className=' '  onSubmit={handleSubmit}> 
    <h1 className='font-[Poppins] font-bold ml-20 ' >KIKHABO?</h1> 
    <InputTextField/>                             
    <PrimaryButton/>
    {error && <p className="error">{error}</p>}
    </form>

    </div>
      
    </div>
  )
}

export default Registration;
