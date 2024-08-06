import React from 'react';
import {Link} from 'react-router-dom';
import { AiOutlineUser} from 'react-icons/ai';

const Header = () => {
  return (
    <div className="flex justify-items-end bg-slate-900 h-14 fixed top-0 w-screen" >
      
      <h1 className='h-12 p-2.5 mt-1 font-bold text-white ml-3' >KIKHABO</h1>
          
      <div className='flex absolute  mr-5'>
      <AiOutlineUser className='ml-96 size-6 text-white ' />  
      <Link to="/profile" className='mr-5 text-white ml-4' >Samantha Hasan </Link>
      </div>
    </div>
  )
}

export default Header;
