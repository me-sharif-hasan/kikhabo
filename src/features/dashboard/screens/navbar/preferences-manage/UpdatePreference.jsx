import React from 'react';
import update_preference from '../../../datasource/update_preference.js';
import InputTextField from './widgets/InputTextField.jsx';
import SaveButton from './widgets/SaveButton.jsx';

const UpdatePreference = () => {
    let meal = new update_preference();
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        let spicyRating = e.target[0].value;
        let budgetRating = e.target[1].value;
        let SaltTasteRating = e.target[2].value;
        let hasDiabetics = e.target[3].value;
        let isPregnant = e.target[4].value;
        let specialNotes = e.target[5].value;
        setLoading(true);
        meal.mealRequest(spicyRating, budgetRating, SaltTasteRating, hasDiabetics, isPregnant, specialNotes).then((response) => {
            console.log(response);
            if (response.status === 'success') {
                setLoading(false);
                <h1>Congratulations!! Your preferences are saved . </h1>
            }
        }).catch((error) => {
            console.log(error);
            setError(error.message);
        })

    };

    return (
        <div className='flex justify-center items-center align-middle'>
            <div className="border mt-14 border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-white bg-opacity-80 relative">
              <form className=' ' onSubmit={handleSubmit}>
                <h1 className='font-[Poppins] font-bold ml-20 '>KIKHABO?</h1>
                <InputTextField/>
                <SaveButton loading={loading}/>
                {error && <p className="error text-wrap">{error}</p>}
              </form>
            </div>
         </div>
    )
}

export default UpdatePreference;
