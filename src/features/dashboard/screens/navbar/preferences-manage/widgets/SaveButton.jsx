import React from 'react'

const SaveButton = () => {
  return (
    <div className={'flex gap-2'} >
        <button className="w-full mb-4 text-[18px] mt-6 rounded-full bg-emerald-700 hover:bg-white py-2 transition-colors duration-300 relative" type="submit">{loading&&<AiOutlineLoading className={'absolute left-16 top-3 animate-spin'}/>}Save</button>      
    </div>
  )
}

export default SaveButton
