import React from 'react';
import { useLocation,Link, Outlet } from 'react-router-dom';
import Preferences from './Preferences';
import { useEffect, useState } from 'react';
import Modal from 'react-modal';
import InputText from '../preferences-manage/widgets/InputTextField.jsx';
import update_preference from '../../../datasource/update_preference.js';


const ManagePrefrences = () => {
  let preference = new update_preference();
  const [error, setError] = useState('');
  const location=useLocation();
  const current_user=location.state;
  const [pbutton, setButton]=useState('');
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!current_user?.data?.preference) {
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
                closeModal();
            }
        }).catch((error) => {
            console.log(error);
            setError(error.message);
        })
    
  };

  return (
    <div>
      <div className='flex relative'>
      {!current_user?.data?.preference ? (
        <h1 className='h-52 mt-0 fixed' >You have no preferences!! Unbelievable!! Please add your preferences.</h1>
      ) : (
        <Preferences/>   
      )}
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
                <h1 className='font-[Poppins] font-bold mt-6 ml-12 pb-5 '>Add Your Preferences</h1>
                <InputText/>
                <button onClick={()=>{setLoading(true);} } className="relative w-52 text-[18px] rounded-full bg-emerald-700 hover:bg-white py-2 transition-colors duration-300" type="submit" disabled={loading}>
                  {loading ? 'Adding...' : 'Submit'} </button>
              </form>
        </div>
        
      </Modal>
      </div>
    </div>
  );
 }

export default ManagePrefrences;
