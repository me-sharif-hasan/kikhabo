import React, { useEffect, useState } from 'react';
import search_family_datasource from '../../../datasource/search_family_datasource.js';
import current_user_datasource from '../../../datasource/current_user_datasource.js';
import Modal from 'react-modal';
import { FaSearch } from 'react-icons/fa';
import FamilyMembers from './FamilyMembers.jsx';

const ManageFamily = () => {
  let myFamily= new search_family_datasource();
  

  const [error, setError] = useState('');
  const [family,setFamily]=useState({});
  const [button, setButton]=useState('');

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [loading, setLoading] = useState(false);

  
  const [input,setInput]=useState('');

  const [debouncedInput, setDebouncedInput] = useState(input); // New state

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedInput(input);
    }, 400); // 300ms delay

    return () => {
      clearTimeout(handler);
    };
  }, [input]);

  useEffect(()=>{
  
   if(debouncedInput){
    myFamily.searchFamily(input).then((response)=>{
      if(response.status==='success'){
        setFamily(response.data);
      }
    }).catch((error)=>{
      console.log(error);  
      setError(error.message);
    });
   }
  },[debouncedInput]);



  const openModal = () => {
    setModalIsOpen(true); }
  const closeModal = () => {
    setModalIsOpen(false); }
  const handleClick = () => {
    console.log('Button is clicked');
    openModal(); };


  return (
    <div>
      <div className='h-96 relative flex'>
      {!family?.members? (
        <h1>You didn't add your family yet! Please Add them!</h1>
      ):(
        <FamilyMembers family={family} />
      )}
      </div>
      <Modal className="flex flex-crelativeol justify-center items-center bg-gray-200 shadow-md border border-gray-300 rounded-xl p-4 absolute fit-content w-[400px] h-[400px] top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
      isOpen={modalIsOpen} ariaHideApp={false}
      onRequestClose={closeModal} >
        <div className='flex fixed top-5 flex-col justify-center items-center align-middle backdrop-filter backdrop-blur-sm'>
        <h1 className='mb-4 font-bold text-lg text-green-900' >Add Your Family Member</h1>
        <div className="flex items-center py-2 h-12 shadow-md rounded-xl bg-white">
        <FaSearch className="ml-6 text-green-900"/>  
        <input  className=" focus:outline-none bg-transparent border-none h-12 w-[280px] rounded-md p-2 ml-1" type="text" placeholder="Type to search.." value={input} onChange={(e)=>setInput(e.target.value)}/>
        </div>
        <div className="flex flex-col rounded-md shadow-md mt-4 overflow-y-scroll max-h-60 w-[320px] bg-white" id='searchresults'>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        <div>Samantha</div>
        </div>

        </div>
        
      </Modal>      

      <button onClick={handleClick} className="fixed bottom-1 w-52 text-[18px] rounded-full ml-96 bg-green-800 hover:bg-white py-2 transition-colors duration-300" >Add New Family</button>
    </div>
  )
}

export default ManageFamily;
