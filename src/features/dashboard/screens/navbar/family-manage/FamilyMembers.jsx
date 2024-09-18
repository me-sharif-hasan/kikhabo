import React from 'react'

const FamilyMembers = ({family}) => {
  return (
    <div className='grid grid-flow-row'>
      <div className='grid grid-cols-3'>{family}</div>
      <div>Hello</div>
    </div>
  )
}

export default FamilyMembers
