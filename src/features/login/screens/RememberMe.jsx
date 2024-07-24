import React from 'react';
import {Link} from 'react-router-dom';

const RememberMe = () => {
  return (
    <div>
       <div className="flex justify-between items-center " >
                <div className="flex gap-2 items-center  " >
                <input  type="checkbox" name="" id="" />
                <label className="text-1xl " htmlFor='Remember Me'>Remember Me</label>
                </div>
                
             </div>

             <div>
              <div className="m-4" >New Here ? <span className='text-pink-800 hover:text-green-900'> <Link to="/register">Create an account .</Link> </span> </div>
             </div>      
    </div>
  )
}

export default RememberMe
