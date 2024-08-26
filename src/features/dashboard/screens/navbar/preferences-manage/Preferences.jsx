import React from 'react';

const Preferences = ({preferences}) => {

  return (
    <div className="mt-3 max-w-sm mx-auto bg-green-300 shadow-lg rounded-lg overflow-hidden transform transition-transform duration-500 hover:scale-105">
    <div className="px-6 py-4">
      <h2 className="text-2xl text-center font-bold text-pink-800 mb-4 animate-pulse">Your Preferences</h2>
      <div className="grid grid-cols-2 gap-4 p-6 bg-gradient-to-r from-green-300 to-emerald-500 rounded-lg shadow-xl animate-fade-in">
      <div className="p-4 bg-white rounded-lg shadow-lg transform transition-transform duration-500 hover:scale-105">
        <h2 className=" text-emerald-700 text-lg font-bold">Spice</h2>
        <p className=" text-gray-700 text-lg ">{preferences.spicyRating}</p>
      </div>
      <div className="p-4 bg-white rounded-lg shadow-lg transform transition-transform duration-500 hover:scale-105">
        <h2 className=" text-emerald-700 text-lg font-bold">Price</h2>
        <p className="text-gray-700 text-lg ">{preferences.budgetRating}</p>
      </div>
      <div className="p-4 bg-white rounded-lg shadow-lg transform transition-transform duration-500 hover:scale-105">
        <h2 className=" text-emerald-700 text-lg font-bold">Salt</h2>
        <p className="text-gray-700 text-lg ">{preferences.saltTasteRating}</p>
      </div>

      <div className="p-4 bg-white rounded-lg shadow-lg transform transition-transform duration-500 hover:scale-105">
        <h2 className=" text-emerald-700 text-lg font-bold">Diabetics</h2>
        <p className="text-gray-700 text-xl ">{preferences.hasDiabetics? 'Yes':'No' }</p>
      </div>

      <div className="p-4 bg-white rounded-lg shadow-lg transform transition-transform duration-500 hover:scale-105">
        <h2 className=" text-emerald-700 text-lg font-bold">Pregnancy</h2>
        <p className="text-gray-700 text-lg">{preferences.isPregnant? 'Yes':'No'}</p>
      </div>

      <div className="p-4 bg-white rounded-lg shadow-lg transform transition-transform duration-500 hover:scale-105">
        <h2 className=" text-emerald-700 text-lg font-bold">Notes</h2>
        <p className="text-gray-700 text-sm ">{preferences.specialNotes}</p>
      </div>

    </div>
  </div>
  </div>
  );
}

export default Preferences;
