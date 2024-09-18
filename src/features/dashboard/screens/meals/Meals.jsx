import React, {useEffect, useRef, useState} from 'react';
import {useLocation} from "react-router-dom";
import {FaDownload} from "react-icons/fa6";
import html2canvas from "html2canvas";

const Meals = () => {
    const {state}=useLocation();
    const {data}=state;
    const [meals,setMeals]=useState(data?.meals);
    let getGroceries = ()=>{
        let groceries = {};
        meals.forEach(meal=>{
            meal.groceries.forEach(grocery=>{
                if(!groceries[grocery.name]){
                    groceries[grocery.name]=parseInt(grocery.amountInGm);
                }else{
                    groceries[grocery.name]+=parseInt(grocery.amountInGm);
                }
            })
        })
        return groceries;
    }

    const listSummary=useRef();
    const [showSummary,setShowSummary]=useState(false);

  return (
    <div className={'relative overflow-y-auto bg-gray-200 px-10 py-2 '}>

        <div className={` ${!showSummary?'hidden':''} bg-gray-100 z-5 p-2 bg-opacity-80 backdrop-blur rounded-md w-full h-full left-0 top-0`}></div>
        <div className={`${!showSummary?'hidden':''} flex justify-center absolute w-full left-0`}>
            <div ref={listSummary} className={`bg-blue-400 text-white z-10 font-sans p-2 mt-2 rounded-md`}>
                <h1 className={'text-xl font-bold'}>Bazar List</h1>
                <p>Total Energy: {meals?.reduce((acc,meal)=>acc+meal.totalEnergy,0)}</p>
                <p>Total Groceries: {meals?.reduce((acc,meal)=>acc+meal.groceries.length,0)}</p>
                <hr className={'my-5'}/>
                <h1 className={'text-lg font-bold uppercase my-2'}>Groceries</h1>
                <div className={'py-2 px-1'}>
                    <div className={'grid grid-cols-2'}>
                        <p className={'font-bold'}>Item</p>
                        <p className={'font-bold'}>Amount</p>
                        {
                            Object.keys(getGroceries()).map((grocery,index)=>{
                                return (
                                    <>
                                        <p>{grocery}</p>
                                        <p>{getGroceries()[grocery]} Gm</p>
                                    </>
                                )
                            })
                        }
                    </div>
                </div>
            </div>
        </div>

        {
            meals?.map((meal,index)=>{
                return (
                    <div key={index} className={'bg-white font-sans p-2 mt-2 rounded-md'}>
                        <h1 className={'text-xl font-bold'}>{meal.mealName}</h1>
                        <p>Total Energy: {meal.totalEnergy}</p>
                        <p>Ingredients: {meal.ingredients}</p>
                        <hr/>
                        <h1 className={'text-lg font-bold uppercase text-gray-800 my-2'}>Groceries</h1>
                        {
                            meal.groceries.map((grocery,index)=>{
                                return (
                                   <div className={'py-2 px-1'}>
                                       <div key={index} className={'flex justify-between'}>
                                           <p>{grocery.name}</p>
                                           <p>{grocery.amountInGm} Gm</p>
                                       </div>
                                       <hr/>
                                   </div>
                                )
                            })
                        }
                        <button className={'bg-orange-500 p-5 rounded-full fixed right-0 bottom-5 text-white'} onClick={e=>{
                            setShowSummary(true);
                            setTimeout(()=>{
                                html2canvas(
                                    listSummary.current
                                ).then(canvas=>{
                                    const imgData=canvas.toDataURL('image/png');
                                    console.log(imgData);
                                    //download
                                    const link=document.createElement('a');
                                    link.href=imgData;
                                    link.download='summary.png';
                                    link.click();
                                });
                            },1000);
                            <button className='bg-violet-800' >Ok</button>
                        }

                        }><FaDownload/></button>
                    </div>
                )
            })
        }
      <p>
      </p>
    </div>
  )
}

export default Meals;
