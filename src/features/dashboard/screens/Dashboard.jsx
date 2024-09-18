import React from 'react';
import Navbar from '../screens/navbar/Navbar.jsx';
import {Outlet} from 'react-router-dom';
import Header from '../screens/header/Header.jsx'

const Dashboard = () => {
  return (
     <div className="bg-dashImage fixed w-full h-[100vh]  bg-no-repeat bg-cover">
      <div className='font-[Poppins]' >
        <Header/>
      </div>

      <div className='mt-14' >
        <Navbar/>
      </div>
      
      <div className='ml-72' >
      <Outlet/>
      </div>
    </div>
  )
}

export default Dashboard;
