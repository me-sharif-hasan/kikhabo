import React, { useEffect, useState } from 'react';
import search_family_datasource from '../../../datasource/search_family_datasource.js';
import add_member_datasource from '../../../datasource/add_member_datasource.js';
import family_datasource from '../../../datasource/family_datasource.js';
import Modal from 'react-modal';
import { FaSearch } from 'react-icons/fa';
import FamilyMembers from './FamilyMembers.jsx';
import NoMember from './NoMember.jsx';

const ManageFamily = () => {
  let searchMember= new search_family_datasource();
  let myFamily=new family_datasource();
  let addMember=new add_member_datasource();

  const [error, setError] = useState('');
  const [family,setFamily]=useState({});
  const [button, setButton]=useState('Add');
  
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [input,setInput]=useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [debouncedInput, setDebouncedInput] = useState(input); 

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedInput(input);
    }, 400); // 400ms delay
    return () => {
      clearTimeout(handler);
    };
  }, [input]);

  useEffect(()=>{ 
   if(debouncedInput){
    searchMember.searchFamily(input).then((response)=>{
      if(response.status==='success'){
        setSearchResults(response.data);
      }else{
        print("Not Found");
        setSearchResults([]);
      }
    }).catch((error)=>{
      console.log(error);  
      setError(error.message);
      setSearchResults([]);
    });
   }
  },[debouncedInput]);

  useEffect(() => {
    myFamily.Family().then((response) => {
      if (response.status === 'success') {
        console.log(response);
        setFamily(response);
      } else {
        setError(response.message);
      }
    }).catch((error) => {
      console.log(error);
      setError(error.message);
    });

  }, [family.members]);
  


  const openModal = () => {
    setModalIsOpen(true); }
  const closeModal = () => {
    setModalIsOpen(false); }
  const handleClick = () => {
    console.log('Button is clicked');
    openModal(); };

  const addOnClick = (id) => {
    console.log('Add button is clicked');
    addMember.addFamilyMember([id]).then((response) => {
      if (response.status === 'success') {
        console.log(response);
        setButton('Added');
      } else {
        setError(response.message);
      }
    }).catch((error) => {
      console.log(error);
      setError(error.message);
    });
  };

  return (
    <div>
      <div className='h-96 relative flex'>
      {family?.members?.length > 0 ? (
       <FamilyMembers family={family} />
       ) : (
       <NoMember/>
       )
      }

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
        {
              searchResults.length > 0 ? (
                searchResults.map((member, index) => (
                  <div key={index} className="flex justify-between items-center p-2 m-1 border-b ">
                     <div className="flex flex-col">
                      <p className='text-green-900' >{member.firstName + " " + member.lastName}</p>
                      <p>{member.email}</p>
                     </div>
                    <button className="bg-green-800 text-white rounded-md p-1" onClick={() => {addOnClick(member.id);} }>{button}</button>
                  </div>
                ))
              ) : (
                <div className="p-2 text-gray-500">No members found</div>
              )
            }
       
        </div>
        </div>      
      </Modal>      

      <button onClick={handleClick} className="fixed bottom-1 w-52 text-[18px] rounded-full ml-96 bg-green-800 hover:bg-white py-2 transition-colors duration-300" >Add New Family</button>
    </div>
  )
}

export default ManageFamily;
