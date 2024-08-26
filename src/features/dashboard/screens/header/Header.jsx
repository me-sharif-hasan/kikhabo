import React from 'react';
import {Link} from 'react-router-dom';
import { AiOutlineUser} from 'react-icons/ai';

const Header = () => {
  //current User Fetching

  return (
    <div className="flex justify-items-end bg-slate-900 h-14 fixed top-0 w-screen" >      
      <div><h1 className='h-12 p-2.5 mt-1 font-bold text-white ml-3' >KIKHABO</h1> </div>          
      <div className='flex ml-96 fixed right-2 top-3'>
      <AiOutlineUser className='right-10 size-6 text-white ' />  

      <Link to="/profile" className='mr-5 text-white ml-4 font-extralight' >Samantha Hasan </Link>
      </div>
    </div>
  )
}

export default Header;
