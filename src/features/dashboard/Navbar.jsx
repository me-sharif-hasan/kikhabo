import React from 'react';
import Form from  './Form.jsx'

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
          <span className='text=[15px] ml-4 text-white font-semibold' >Home</span>
        </div>

        <div className='p-2.5 mt-3 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' >Total Calorie</span>
        </div>

        <div className='p-2.5 mt-3 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' >Cost</span>
        </div>

        <div className='p-2.5 mt-40 flex items-center rounded-md px-4
        duration-300 cursor-pointer hover:bg-green-500 text-white' >
          <i className='bi bi-house-door-fill' ></i>
          <span className='text=[15px] ml-4 text-white font-semibold' >Logout</span>
        </div>

      </div>
      <Form/>

    </div>
    
  )
}

export default Navbar;
