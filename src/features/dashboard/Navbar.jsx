import React from 'react';
import { AiFillApple, AiFillHome, AiOutlineLogout } from 'react-icons/ai';
import {Link} from 'react-router-dom';

const Navbar = () => {
  return (
    <div className='font-[Poppins]' >
      <div className='sidebar fixed top-0 h-screen lg:left-0 p-2 w-[300px] overflow-y-auto 
      text-center bg-gray-600' >
        <div className='text-white-900 text-xl'>
          <div className='shadow-md p-2.5 mt-1 flex items-center' > 
            <h1 className='font-bold text-white ml-3' >KIKHABO</h1>
            <p className='ml-20 text-white cursor-pointer' >x</p>
          </div>
          <hr className='' ></hr>
        </div>

        <div className='p-2.5 mt-3 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <AiFillHome/>
          <span className='text=[15px] ml-4 text-white font-semibold' > 
          <Link to="/home">Home</Link></span>
        </div>

        <div className='p-2.5 mt-3 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <AiFillApple/>
          <span className='text=[15px] ml-4 text-white font-semibold' >
            <Link to="/meal_stat">Meal Statistics</Link></span>
        </div>

        <div className='p-2.5 mt-3 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' >
            <Link to="/cost">Cost</Link></span>
        </div>

        <div className='p-2.5 bottom-0 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <AiOutlineLogout/>
          <span className='pr-8 text=[15px] ml-4 text-white font-semibold' >
          <Link to="">Logout</Link></span>
        </div>
      </div>
    </div>    
  )
}
export default Navbar;
