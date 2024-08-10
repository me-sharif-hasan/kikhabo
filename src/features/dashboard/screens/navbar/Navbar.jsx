import { useState } from 'react';
import React from 'react';
import { AiFillApi, AiFillHeart, AiOutlineDashboard, AiOutlineDollar, AiOutlineLogout, AiOutlineTeam } from 'react-icons/ai';
import {Link} from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import current_user_datasource from '../../datasource/current_user_datasource';


const Navbar = () => {

    let current_user = new current_user_datasource();
    const [error, setError] = useState('');
    const [activeLink, setActiveLink] = useState('');
    const navigate = useNavigate();
   

    const handleClick = async (e) => {
        e.preventDefault();
        setActiveLink('/manage_preferences');
        current_user.currentUser().then((response) => {
            console.log(response);
            if (response.status === 'success') {
                navigate('/dashboard/manage_preferences', {state:response});
            }
        }).catch((error) => {
            console.log(error);
            setError(error.message);
        })

    };


  
  return (
    <div className='font-comforta' >
      <div className='sidebar fixed h-screen lg:left-0 w-[300px] overflow-y-auto 
      text-center bg-gray-600' >
        <div className='text-white-900 text-xl'>
        </div>

      <Link to="/dashboard/home" className={` ${activeLink === '/home' ? 'bg-orange-600' : 'text-white'}`} 
          onClick={() => setActiveLink('/home')} >  
        <div className='p-2.5 flex items-center px-4 
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiOutlineDashboard/>
          <span className='text=[15px] ml-4 text-white ' > 
          Dashboard</span>
        </div></Link>

        <Link to="/dashboard/meal_stat" className={`${activeLink === '/meal_stat' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={() => setActiveLink('/meal_stat')}>
        <div className='p-2.5 mt-3 flex items-center px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiFillApi />
          <span className='text=[15px] ml-4 text-white' >
            Meal Statistics</span>
        </div>
        </Link>

        <Link to="/dashboard/cost" className={`${activeLink === '/cost' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={() => setActiveLink('/cost')}>
        <div className='p-2.5 mt-3 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiOutlineDollar/>
          <span className='text=[15px] ml-4 text-white ' >
            Monthly Costs</span>
        </div> </Link>


        <Link to="/dashboard/manage_family" className={` ${activeLink === '/manage_family' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={() => setActiveLink('/manage_family')}>
        <div className='p-2.5 mt-3 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiOutlineTeam/>  
          <span className='text=[15px] ml-4 text-white ' >Manage Family</span>
        </div></Link>

        <Link to="/dashboard/manage_preferences"  className={`${activeLink === '/manage_preferences' ? 'bg-orange-600' : 'text-white'}`} 
            onClick={handleClick}>
        <div className='p-2.5 mt-3 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-orange-600 text-white' >
          <AiFillHeart/>
          <span className='text=[15px] ml-4 text-white ' >
            Manage Preferences</span>
        </div></Link>

        <Link to="/">
        <div className='bottom-6 fixed p-2.5 flex items-center  px-4
        duration-300 cursor-pointer hover:bg-red-600 text-white' >
          <AiOutlineLogout className='ml-16' />
          <span className='relative text-[15px] ml-4 text-white ' >
          Logout</span>
        </div>
        </Link>

      </div>
    </div>    
  )
}
export default Navbar;
