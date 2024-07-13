import React from 'react';
import '../index.css' ;

import { Routes, Route } from 'react-router-dom';
import Dashboard from './features/dashboard/Dashboard.jsx';
import LoginPage from './features/login/screens/LoginPage.jsx';


function App() {
  return (
    <>

      <Routes>
      <Route path="/" element={<LoginPage/>} />
      <Route path="/dashboard" element={<Dashboard />} />
     </Routes>


    </>

  )
}

export default App
