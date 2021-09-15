import React from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";

import axios from 'axios'


function recommendation3(){

    const history = useHistory();
    const location = useLocation();

    const submitClicked = () =>{

        axios.post('http://localhost:8080/users/histories',{
            "userId" : location.state.email,
            "temperature" : location.state.current,
            "clothesId" : location.state.clothes_number,
            "color" : location.state.color,
            "colorMix" : location.state.selectedColor,
            "baseDate" : "20210915",
            "baseTime" : "2200"
        },{
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`
            }
        }).then(res=>{
            console.log(res.data)
            history.push('/nalot/recommendation_location',{
                "key": location.state.key,
                "email": location.state.email,
            })
        })
    }
    const prevClicked = () =>{
        history.push('/nalot/recommendation2',{
            "email": location.state.email,
            "key": location.state.key,
            "current":location.state.current,
            "data":location.state.data,
            "clothes":location.state.clothes,
            "clothes_number":location.state.clothes_number,
            "toneintone" : location.state.toneintone,
            "toneontone" : location.state.toneontone
        })
    }
    console.log(location.state.color)

    return(
        <div>
            <Header/>
            <Menu/>
            <div>
                선택한 의상 : {location.state.clothes}
            </div>
            <div>
                선택한 색상
                <button name = "button1" style={
                    {
                        background: location.state.color
                    }
                }>
                    {location.state.color}
                </button>

            </div>
            <div>
                추천 색상
                <button name = "button" style={
                    {
                        background: location.state.selectedColor
                    }
                }>
                    {location.state.selectedColor}
                </button>
            </div>
            <div>
                <button
                    onClick={prevClicked}
                >이전</button>
                <button
                    onClick={submitClicked}
                >
                    저장
                </button>
            </div>
        </div>
    )
}

export default recommendation3;