import React from 'react';
import { useLocation,Link } from 'react-router-dom';
import Preferences from './Preferences';
import UpdateButton from './widgets/UpdateButton';

const ManagePrefrences = () => {
  const location=useLocation();
  const current_user=location.state;

  return (
    <div>
      <div className='flex absolute'>
      {!current_user?.data?.preference ? (
        <h1>You have no preferences!! Unbelievable!! Please add your preferences.</h1>
      ) : (
        <Preferences/>
      )}
      <div className='flex relative mt-96 ml-60'>
      <UpdateButton/>
      </div>
      </div>
    </div>
  );
 }

export default ManagePrefrences;
