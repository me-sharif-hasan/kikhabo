import React from 'react';
import Navbar from './Navbar';
import {Outlet} from 'react-router-dom';


const Dashboard = () => {
  return (
     <div className="bg-dashImage  w-full h-[100vh]  bg-no-repeat bg-cover">
      <Navbar/>
      <div className='' >
      <Outlet/>
      </div>
      
    </div>
  )
}

export default Dashboard;
