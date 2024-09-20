import React from 'react'
import family_datasource from "../../../datasource/family_datasource.js";

const FamilyMembers = ({family,onClose}) => {

  const familyMemberDatasource=new family_datasource();

  return (
    <div className='grid grid-cols-3 gap-2'>
        {
            family?.map((member) => {
                return <div className='flex flex-col bg-green-200 rounded p-2 shadow'>
                    <div>{member.firstName} {member.lastName}</div>
                    <div>{member.email}</div>
                    <div>{member.gender}</div>
                  <div className={'flex justify-end'}>
                    <button className='bg-red-500 p-2 rounded-md text-white' onClick={()=>{
                        familyMemberDatasource.deleteFamilyMember(member.id).then((response)=>{
                            onClose();
                        }).catch((error)=>{
                            console.log(error);
                        })
                    }}>Delete</button>
                  </div>
                </div>
            })
        }
    </div>
  )
}

export default FamilyMembers
