import React, {useState} from 'react';
import {Route, Routes, useNavigate} from 'react-router-dom';
import InputText from './widgets/InputText.jsx';
import SuggestButton from './widgets/SuggestButton.jsx';
import dashboard_datasource from './datasource/dashboard_datasource.js';

const Form = () => {

    let meal = new dashboard_datasource();

    const [error, setError] = useState('');
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);
    const handleSubmit = async (e) => {
        e.preventDefault();
        let spicyRating = e.target[0].value;
        let saltRating = e.target[1].value;
        let dayCount = e.target[2].value;
        let priceRating = e.target[3].value;
        let totalMealCount = e.target[4].value;
        let agesOfTheMembers = e.target[5].value;
        setLoading(true);
        meal.mealRequest(spicyRating, saltRating, dayCount, priceRating, totalMealCount, agesOfTheMembers).then((response) => {
            console.log(response);

            if (response.status === 'success') {
                setLoading(false);
                navigate('/dashboard/meals', {state:response});
            }
        }).catch((error) => {
            console.log(error);
            setError(error.message);
        })

    };


    return (
        <Routes>
          <Route path="/" element={<div className='ml-[300px] flex justify-center items-center '>

            <div
                className=" mt-24 border border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-white bg-opacity-80 relative">
              <form className=' ' onSubmit={handleSubmit}>
                <h1 className='font-[Poppins] font-bold ml-20 '>KIKHABO?</h1>
                <InputText/>
                <SuggestButton loading={loading}/>
                {error && <p className="error">{error}</p>}
              </form>

            </div>

          </div>}/>
        </Routes>
    )
}

export default Form;
