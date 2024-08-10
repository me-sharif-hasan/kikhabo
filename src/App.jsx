import React from 'react';
import '../index.css';
import { Routes, Route } from 'react-router-dom';
import Dashboard from './features/dashboard/screens/Dashboard.jsx';
import LoginPage from './features/login/screens/LoginPage.jsx';
import Meals from './features/dashboard/screens/meals/Meals.jsx';
import Registration from './features/registration/screens/Registration.jsx';
import Form from "./features/dashboard/screens/Form.jsx";
import Profile from "./features/dashboard/account/SetAccount.jsx"
import ManageFamily from './features/dashboard/screens/navbar/family-manage/ManageFamily.jsx';
import ManagePrefrences from './features/dashboard/screens/navbar/preferences-manage/ManagePrefrences.jsx';
import MealStat from './features/dashboard/screens/navbar/statistics_meal/MealStat.jsx';
import Costs from './features/dashboard/screens/navbar/total_costs/Costs.jsx';
import UpdatePreference from './features/dashboard/screens/navbar/preferences-manage/UpdatePreference.jsx';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} >
          <Route path={"meals"} element={<Meals />} />
          <Route path={""} element={<Form />} />
          <Route path={"home"} element={<Form/> } />
          <Route path={"manage_family"} element={<ManageFamily/> } />

          <Route path={"manage_preferences"} element={<ManagePrefrences/> } >
            <Route path={"update_pref"} element={<UpdatePreference/>} />
          </Route>

          <Route path={"meal_stat"} element={<MealStat/> }/>
          <Route path={"cost"} element={<Costs/> }/>
        </Route>
        <Route path="/register" element={<Registration />} />
        
        <Route path='/profile' element={<Profile/>}/>
      </Routes>
    </>
  )
}

export default App;