import React from 'react';
import '../index.css' ;

import { Routes, Route } from 'react-router-dom';
import Dashboard from './features/dashboard/Dashboard.jsx';
import LoginPage from './features/login/screens/LoginPage.jsx';
import Meals from './features/dashboard/screens/Meals.jsx';
import Registration from './features/registration/screens/Registration.jsx';
import Home from './features/dashboard/screens/navbar/Home.jsx';
import Form from "./features/dashboard/Form.jsx";


function App() {
  return (
    <>

      <Routes>
      <Route path="/" element={ <LoginPage/>  } />
      <Route path="/dashboard" element={<Dashboard />} >
          <Route path={"meals"} element={<Meals/>}/>
          <Route path={""} element={<Form/>}/>
          
      </Route>
      {/*<Route path="/meals" element={<Meals/>}/>*/}
      <Route path="/register" element={<Registration/>}/>
      <Route path='/home' element={<Home/>}/>


     </Routes>


    </>

  )
}

export default App
