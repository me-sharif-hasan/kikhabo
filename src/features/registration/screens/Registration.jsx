import React from 'react';
import InputTextField from '../widgets/InputTextField.jsx';
import PrimaryButton from  '../widgets/PrimaryButton.jsx';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import registration_datasource from '../datasource/registration_datasource.js';

const Registration = () => {
  let register=new registration_datasource();
  const [error, setError] = useState('');
  const navigate = useNavigate();


  const handleSubmit = async (e) => {
    e.preventDefault();
    
    
    let email=e.target[0].value;
    let password=e.target[1].value;
    let firstName=e.target[2].value;
    let lastName=e.target[3].value;
    let country=e.target[4].value;
    let gender=e.target[5].value;
    let religion=e.target[6].value;
    let dateOfBirth=e.target[7].value;
    let weightInKg=e.target[8].value;
    let heightInFt=e.target[9].value;



    register.doRegister(email,password,firstName,lastName,country,gender,religion, dateOfBirth,weightInKg, heightInFt).then((response)=>{
      console.log(response);
      if(!isNaN(response.id)) {
        navigate('/');
      }
    }).catch((error)=>{
      console.log(error);
      setError(error.message);
    })
     
  };

  return (
    <div>
    <div className="text-black-900 bg-bgImage  w-full h-[100vh] flex justify-center items-center bg-no-repeat bg-center">
    <div className=" bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">        
    <form className=' '  onSubmit={handleSubmit}> 
    <h1 className='font-[Poppins] font-bold ml-20 ' >KIKHABO?</h1> 
    <InputTextField/>                             
    <PrimaryButton/>
    {error && <p className="error">{error}</p>}
    </form>

    </div>   
    </div>
    </div>
  )
}

export default Registration;