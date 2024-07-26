import React from 'react';
import Form from  './Form.jsx';
import {Link, Outlet} from 'react-router-dom';

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
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' > <Link to="/home">Home</Link></span>
        </div>

        <div className='p-2.5 mt-3 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' ><Link to="/meal_stat">Meal Statistics</Link></span>
        </div>

        <div className='p-2.5 mt-3 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' ><Link to="/cost">Cost</Link></span>
        </div>

        <div className='p-2.5 mt-40 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' ><Link to="">Logout</Link></span>
        </div>

      </div>
      <Outlet/>
    </div>
    
  )
}

export default Navbar;
