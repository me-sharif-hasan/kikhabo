import React from 'react';
import { useLocation } from 'react-router-dom';

const ManagePrefrences = () => {
  const {state}=useLocation();
    const {data}=state;
    const [pref,setMeals]=useState(data?.meals);

  if(!data.preference){
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
