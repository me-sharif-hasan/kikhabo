import React from 'react';
import { useLocation } from 'react-router-dom';

const ManagePrefrences = () => {
  const location=useLocation();
  const current_user=location.state;

  if(!current_user.data.preference){
    return (
      <div>
        <h1>You have no preferences!! Unbelievable!! Please add your preferences .</h1>
      </div>
    );
  }

  return (
    <div>
      <h1>Your Preferences</h1>
    </div>
  )
}

export default ManagePrefrences;
