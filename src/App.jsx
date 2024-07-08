import React from 'react';
import backgroundImage from './assets/bg.jpeg';

function App() {
  return (
    <>
       <div
      className="w-full h-screen bg-cover bg-center"
      style={{ backgroundImage: `url(${backgroundImage})` }}
    >
     <div className="font-bold ">KI KHABO</div> 
    </div>
     
    </>
  )
}

export default App
