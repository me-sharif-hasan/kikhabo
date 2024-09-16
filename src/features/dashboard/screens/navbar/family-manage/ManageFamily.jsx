import React, { useEffect, useState } from 'react';
import family_datasource from '../../../datasource/family_datasource.js';
import current_user_datasource from '../../../datasource/current_user_datasource.js';
import Modal from 'react-modal';

const ManageFamily = () => {
  let myFamily= new family_datasource();
  

  const [error, setError] = useState('');
  const [family,setFamily]=useState({});
  const [button, setButton]=useState('');

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [loadData, setLoadData] = useState(false);

  const [email, setEmail]=useState('');
  const [name,setname]=useState('');

  useEffect(()=>{
    myFamily.manageFamily().then((response)=>{
      console.log(response);
      if(response.status==='success'){
        setFamily(response.data);
      }
    }).catch((error)=>{
      console.log(error);  
      setError(error.message);
    })
  },[loadData]);



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
      <Modal className="flex flex-crelativeol} justify-center items-center bg-white shadow-md border border-gray-300 rounded-xl p-4 absolute fit-content w-[400px] h-[400px] top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
      isOpen={modalIsOpen} ariaHideApp={false}
      onRequestClose={closeModal}  >
        <div className='flex relative flex-col justify-center items-center align-middle backdrop-filter backdrop-blur-sm'>
        <h1>This is Modal</h1>
        </div>
        
      </Modal>      

      <button onClick={handleClick} className="fixed bottom-1 w-52 text-[18px] rounded-full ml-96 bg-green-800 hover:bg-white py-2 transition-colors duration-300" >Add New Family</button>
    </div>
  )
}

export default ManageFamily;
