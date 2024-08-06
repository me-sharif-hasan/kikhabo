import React from 'react'

const InputTextField = () => {
  return (
    <div>
      <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-black transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Rate your spicy taste</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className="absolute text-xl text-black transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Suitable Price Rate</label>
            </div>

            <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-black transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Rate your salty taste</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className="absolute text-xl text-black transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Have you Diabetics ?</label>
            </div>

            <div className="relative my-4 " >
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="number"/>
                <label className=" absolute text-xl text-black transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Are you Pregnant ?</label>
            </div>
            <div  className="relative my-4">
                <input   className="block w-72 py-2.3 px-0 text-sm text-black bg-transparent border-0 border-b-2 border-green-800 appearance-none dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:text-black focus:border-green-600 peer " type="text"/>
                <label className="absolute text-xl text-black transform scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-focus:scale-75 -translate-y-8 duration-500" htmlFor=''>Any special notes ?</label>
            </div>
    </div>
  )
}

export default InputTextField;
