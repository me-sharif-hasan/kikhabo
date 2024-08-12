import React from 'react';
import { useLocation,Link, Outlet } from 'react-router-dom';
import Preferences from './Preferences';
import { useEffect, useState } from 'react';



const ManagePrefrences = () => {
  const location=useLocation();
  const current_user=location.state;
  const [pbutton, setButton]=useState('');

  const handleClick = () => {
    console.log('Button is clicked');
    
    
  };
  useEffect(() => {
    if (!current_user?.data?.preference) {
      setButton('Add Preferences');
    } else {
      setButton('Update Preferences');
    }
  }, [current_user]);

  return (
    <div>
      <div className='flex relative'>
      {!current_user?.data?.preference ? (
        <h1 className='h-52 mt-0 fixed' >You have no preferences!! Unbelievable!! Please add your preferences.</h1>
      ) : (
        <Preferences/>   
      )}
      </div>
      
      <div className='flex relative mt-96 ml-96 '>
      <button onClick={handleClick} className="relative w-52 text-[18px] rounded-full bg-emerald-700 hover:bg-white py-2 transition-colors duration-300" >{pbutton}</button>
      </div>
    </div>
  );
 }

export default ManagePrefrences;
