import React , { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import InputText from './widgets/InputText.jsx';
import SuggestButton from './widgets/SuggestButton.jsx';
import dashboard_datasource from './datasource/dashboard_datasource.js';

const Form = () => {

  let meal= new dashboard_datasource();

  const [error, setError] = useState('');
  const navigate = useNavigate();


  const handleSubmit = async (e) => {
    e.preventDefault();
    let spicyRating=e.target[0].value;
    let saltRating=e.target[1].value;
    let dayCount=e.target[2].value;
    let priceRating=e.target[3].value;
    let totalMealCount=e.target[4].value;
    let agesOfTheMembers=e.target[5].value;

    meal.mealRequest(spicyRating,saltRating,dayCount,priceRating,totalMealCount,agesOfTheMembers).then((response)=>{
      console.log(response);
      response.status='success';
      if(response.status=='success'){
        navigate('/meals')
      }
    }).catch((error)=>{
      console.log(error);
      setError(error.message);
    })
     
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
