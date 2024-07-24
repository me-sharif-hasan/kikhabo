import React from 'react';
import '../index.css' ;

import { Routes, Route } from 'react-router-dom';
import Dashboard from './features/dashboard/Dashboard.jsx';
import LoginPage from './features/login/screens/LoginPage.jsx';
import Meals from './features/dashboard/screens/Meals.jsx';
import Registration from './features/registration/screens/Registration.jsx';


function App() {
  return (
    <>

      <Routes>
      <Route path="/" element={ <LoginPage/>  } />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/meals" element={<Meals/>}/>
      <Route path="/register" element={<Registration/>}/>
     </Routes>


    </>

  )
}

export default App
