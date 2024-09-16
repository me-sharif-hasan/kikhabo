import React from 'react';
import {Link} from 'react-router-dom';
import { AiOutlineUser} from 'react-icons/ai';
import { useEffect, useState } from 'react';
import current_user_datasource from './../../datasource/current_user_datasource.js';

const Header = () => {
  //current User Fetching
  const [current_user,setCurrentUser]=useState({});
  const [name, setName] = useState('');
  const [error, setError] = useState('');
  let user = new current_user_datasource();

  useEffect(() => {
    user.currentUser().then((response) => {
      console.log(response);
      if (response.status === 'success') {
          setCurrentUser(response.data);
          if(response.data){
          setName(response.data?.firstName+" "+response.data?.lastName); }else{
            setName('User');
          }
      }
  }).catch((error) => {
      console.log(error);
      setError(error.message);
  })
  }, [current_user]);

  return (
    <div className="flex justify-items-end bg-slate-900 h-14 fixed top-0 w-screen" >      
      <div><h1 className='h-12 p-2.5 mt-1 font-bold text-white ml-3' >KIKHABO</h1> </div>          
      <div className='flex ml-96 fixed right-2 top-3'>
      <AiOutlineUser className='right-10 size-6 text-white ' />  

      <Link to="/profile" className='mr-5 text-white ml-4 font-medium ' >{name} </Link>
      </div>
    </div>
  )
}

export default Header;
