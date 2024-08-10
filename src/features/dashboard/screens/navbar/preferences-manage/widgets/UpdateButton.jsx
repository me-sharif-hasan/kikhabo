import React from 'react';
import { Link } from 'react-router-dom';

const UpdateButton = () => {
 

  return (
    <div>
      <Link to='/dashboard/manage_preferences/update_pref'>
      <button className="relative w-32 text-[18px] rounded-full bg-emerald-700 hover:bg-white py-2 transition-colors duration-300" type="submit">Update</button>
      </Link>  
    </div>
  )
}

export default UpdateButton;
