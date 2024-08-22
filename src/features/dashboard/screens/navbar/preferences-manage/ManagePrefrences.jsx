import React from 'react';
import Preferences from './Preferences';
import { useEffect, useState } from 'react';
import Modal from 'react-modal';
import update_preference from '../../../datasource/update_preference.js';
import current_user_datasource from '../../../datasource/current_user_datasource.js';

const ManagePrefrences = () => {
  let preference = new update_preference();
  let user = new current_user_datasource();
  const [error, setError] = useState('');
  const [current_user,setCurrentUser]=useState({});
  const [pbutton, setButton]=useState('');
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [loadData, setLoadData] = useState(false);

  useEffect(() => {
    user.currentUser().then((response) => {
      console.log(response);
      if (response.status === 'success') {
          setCurrentUser(response.data);
      }
  }).catch((error) => {
      console.log(error);
      setError(error.message);
  })
  }, [loadData]);

  useEffect(() => {
    if (!current_user?.preference) {
      setButton('Add Preferences'); }  else {
      setButton('Update Preferences');}
  }, [current_user]);

  const openModal = () => {
    setModalIsOpen(true); }
  const closeModal = () => {
    setModalIsOpen(false); }
  const handleClick = () => {
    console.log('Button is clicked');
    openModal(); };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form is submitted');
        let spicyRating = e.target[0].value;
        let priceRating = e.target[1].value;
        let saltRating = e.target[2].value;
        let hasDiabets = e.target[3].value;
        let isPregnant = e.target[4].value;
        let specialNotes = e.target[5].value;
        setLoading(true);
        preference.setPreference(spicyRating,priceRating,saltRating,hasDiabets,isPregnant, specialNotes).then((response) => {
            console.log(response);
            if (response.status === 'success') {
                setLoading(false);
                <h1>Congratulations!! Your preferences are saved . </h1>
                setLoadData(!loadData);
                closeModal();
            }
        }).catch((error) => {
            console.log(error);
            setError(error.message);
        });
      };

  return (
    <div>
      <div className='flex relative'>
        <p>{JSON.stringify(current_user?.preference.spicyRating)} </p>

      {!current_user?.preference ? (
        <h1 className='h-52 mt-0 fixed' >You have no preferences!! Unbelievable!! Please add your preferences.</h1>
      ) : (
        <Preferences />   
      )}!current_user?.preference ? (
        <h1 className='h-52 mt-0 fixed' >You have no preferences!! Unbelievable!! Please add your preferences.</h1>
      ) : (
        <Preferences />   
      )
      </div>
      <div className='flex relative mt-96 ml-96 align-middle '>
      <button onClick={handleClick} className="relative w-52 text-[18px] rounded-full bg-emerald-700 hover:bg-white py-2 transition-colors duration-300" >{pbutton}</button>
      <Modal className="flex flex-crelativeol} justify-center items-center bg-white shadow-md border border-gray-300 rounded-xl p-4 absolute fit-content w-[400px] h-[400px] top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
      isOpen={modalIsOpen}
      onRequestClose={closeModal} 
      >
        <div className="flex relative flex-col justify-center items-center align-middle backdrop-filter backdrop-blur-sm">
        <button className='absolute right-0 top-0 hover:bg-slate-200 rounded-full w-8 h-8' onClick={closeModal}>x</button>
        <form className=' ' onSubmit={handleSubmit}>
                <h1 className='font-[Poppins] font-bold mt-6 ml-12 pb-5 text-gray-700 '>Add Your Preferences</h1>
                {/* Startting of the input fields */}
                <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " value={!current_user?.preference.spicyRating ? (null ) : ( current_user?.preference.spicyRating)} type="number"/>
                <label className=" absolute text-xl text-gray-700 transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Rate your spicy taste</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " value={4} type="number"/>
                <label className="absolute text-xl text-gray-700 transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Suitable Price Rate</label>
            </div>

            <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-gray-700 transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Rate your salty taste</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className="absolute text-xl text-gray-700 transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Have you Diabetics ?</label>
            </div>

            <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-gray-700 transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Are you Pregnant ?</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="text"/>
                <label className="absolute text-xl text-gray-700 transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Any special notes ?</label>
            </div>
             {/* Ending of the input fields */}
                <button onClick={()=>{setLoading(true);} } className="ml-10 relative w-52 text-[18px] rounded-full bg-emerald-700 hover:bg-white py-2 transition-colors duration-300" type="submit" disabled={loading}>
                  {loading ? 'Adding...' : 'Submit'} </button>
              </form>
        </div>
        
      </Modal>
      </div>
    </div>
  );
 }

export default ManagePrefrences;
