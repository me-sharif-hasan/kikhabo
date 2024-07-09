import React from 'react'
import { Link } from 'react-router-dom';

const LoginPage = () => {
  return (
    <div className="flex items-center h-96">
        <div className="h-96 w-1/2 mx-auto my-auto bg-slate-800 border-slate-400 rounded-md p-8 shadow-lg backdrop-filter backdrop-blur-sm bg-opacity-30 relative flex justify-center items-center align-middle ">
            <h1 className="mt-8" >Login</h1>
            <form action="">
            <div>
                <input type="email"/>
                <label htmlFor=''>Your Email</label>
            </div>
            <div>
                <input type="password"/>
                <label htmlFor=''>Your Password</label>
            </div>
            <div>
                <div>
                <input type="checkbox" name="" id="" />
                <label htmlFor='Remember Me'>Remember Me</label>
                </div>
                <span>Forget password ?</span>
             </div>
             <button type="submit">Login</button>
              
             <div>
              <span>New Here ? <Link to="Register">Create an account .</Link> </span>
              </div> 

            </form>

        </div>
    </div>
  );
};

export default LoginPage;
