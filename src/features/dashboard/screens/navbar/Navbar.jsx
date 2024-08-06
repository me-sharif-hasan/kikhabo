import { useState } from 'react';
import React from 'react';
import { AiFillApi, AiFillHeart, AiOutlineDashboard, AiOutlineDollar, AiOutlineLogout, AiOutlineTeam } from 'react-icons/ai';
import { GoArrowLeft } from "react-icons/go";
import {Link} from 'react-router-dom';


const Navbar = () => {

  const [activeLink, setActiveLink] = useState('');

  return (

    <div className='font-comforta' >
      <div className='sidebar fixed h-screen lg:left-0 p-2 w-[300px] overflow-y-auto 
      text-center bg-gray-600' >
        <div className='text-white-900 text-xl'>
        </div>

        <div className='p-2.5 mt-3 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiOutlineDashboard/>
          <span className='text=[15px] ml-4 text-white ' > 
          <Link to="/dashboard/home" className={` ${activeLink === '/home' ? 'bg-orange-600' : 'text-white'}`} 
          onClick={() => setActiveLink('/home')} >Dashboard</Link></span>
          <GoArrowLeft className='ml-16 cursor-pointer ' />
        </div>

        <div className='p-2.5 mt-3 flex items-center px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiFillApi />
          <span className='text=[15px] ml-4 text-white' >
            <Link to="/dashboard/meal_stat" className={`${activeLink === '/meal_stat' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={() => setActiveLink('/meal_stat')}>Meal Statistics</Link></span>
        </div>

        <div className='p-2.5 mt-3 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiOutlineDollar/>
          <span className='text=[15px] ml-4 text-white ' >
            <Link to="/dashboard/cost" className={`${activeLink === '/cost' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={() => setActiveLink('/cost')}>Monthly Costs</Link></span>
        </div>

        <div className='p-2.5 mt-3 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiOutlineTeam/>  
          <span className='text=[15px] ml-4 text-white ' >
            <Link to="/dashboard/manage_family" className={` ${activeLink === '/manage_family' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={() => setActiveLink('/manage_family')}>Manage Family</Link></span>
        </div>

        <div className='p-2.5 mt-3 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiFillHeart/>
          <span className='text=[15px] ml-4 text-white ' >
            <Link to="/dashboard/manage_preferences"  className={`${activeLink === '/manage_preferences' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={() => setActiveLink('/manage_preferences')}>Manage Preferences</Link></span>
        </div>

        <div className='ml-8 bottom-6 relative p-2.5 mt-32 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-red-600 text-white' >
          <AiOutlineLogout className='ml-16' />
          <span className='pr-8 relative text=[15px] ml-4 text-white ' >
          <Link to="/">Logout</Link></span>
        </div>
       

      </div>
    </div>    
  )
}
export default Navbar;
