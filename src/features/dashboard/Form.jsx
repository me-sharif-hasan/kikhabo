import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import InputText from './widgets/InputText.jsx';
import SuggestButton from './widgets/SuggestButton.jsx'

const Form = () => {

  const [error, setError] = useState('');
  const navigate = useNavigate();


  const handleSubmit = async (e) => {
    e.preventDefault();
    <p>Submitted</p>
     
  };



  return (
    <div className='ml-[300px] flex justify-center items-center ' >

    <div className=" mt-24 bg-slate-800 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-10 relative">        
    <form className=' '  onSubmit={handleSubmit}> 
    <h1 className='font-[Poppins] font-bold ml-20 ' >KIKHABO?</h1> 
    <InputText/>                             
    <SuggestButton/>
    {error && <p className="error">{error}</p>}
    </form>

</div>

    </div>
  )
}

export default Form;
