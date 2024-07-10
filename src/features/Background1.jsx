import React from 'react'
import LoginPage from './login/screens/LoginPage'
import {Route, Routes } from 'react-router-dom';

const Background1 = () => {
  return ( 
      <div className="text-black-900 bg-bgImage  w-full h-[100vh] flex justify-center items-center bg-no-repeat bg-center">
        

        <LoginPage/>
      </div>
  )
}

export default Background1
