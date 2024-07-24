import React from 'react'

const InputTextField = () => {
  return (
    <div>
       <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-black transform -translate-y-5 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 peer-focus:-translate-y-8 duration-500" htmlFor=''>First Name</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className="absolute text-xl text-black transform -translate-y-5 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 peer-focus:-translate-y-8 duration-500" htmlFor=''>Last Name</label>
            </div>

            <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-black transform -translate-y-5 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 peer-focus:-translate-y-8 duration-500" htmlFor=''>Enter your email</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className="absolute text-xl text-black transform -translate-y-5 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 peer-focus:-translate-y-8 duration-500" htmlFor=''>Suitable Price Rate</label>
            </div>

            <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-black transform -translate-y-5 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 peer-focus:-translate-y-8 duration-500" htmlFor=''>Total Meal in a Day</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="text"/>
                <label className="absolute text-xl text-black transform -translate-y-5 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 peer-focus:-translate-y-8 duration-500" htmlFor=''>Ages of your family member</label>
            </div>

    </div>
  )
}

export default InputTextField;
