import React from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from 'axios'
import Button from "@material-ui/core/Button";
import Result from "../Components/Result";
import Footer from "../Components/Footer";
import app from "../resources/application.json"

function recommendation3(){

    const history = useHistory();
    const location = useLocation();

    const submitClicked = () =>{
        console.log(location.state)
        axios.post(app.ip+'/users/histories',{
            "userId" : location.state.email,
            "temperature" : location.state.current,
            "clothesId" : location.state.clothes_number,
            "color" : location.state.color,
            "colorMix" : location.state.selectedColor,
            "baseDate" : location.state.baseDate,
            "baseTime" : location.state.baseTime,
        },{
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`
            }
        }).then(res=>{
            console.log(res.data)
            alert(res.data.message)
            history.push('/nalot/main',{
                "key": location.state.key,
                "email": location.state.email,
            })
        }).catch(err=>{
            alert(err.response.data.error)
            history.push('/nalot')
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
            <h1>?????? ??????</h1>
            <Result/>
            <div
                style={{
                    margin:'30px'
                }}
            >
                <div>
                    ????????? ?????? : <h2>{location.state.clothes}</h2>
                </div>
                <div>
                    ????????? ??????
                    <p>
                        <Button name = "button1" style={
                            {
                                background: location.state.color
                            }
                        }>
                            {location.state.color}
                        </Button>
                    </p>

                </div>
                <div>
                    ????????? ?????? ??????
                    <p>
                        <Button name = "button" style={
                            {
                                background: location.state.selectedColor
                            }
                        }>
                            {location.state.selectedColor}
                        </Button>
                    </p>

                </div>
                <div>
                    <Button
                        onClick={prevClicked}
                        variant="outlined"
                    >??????</Button>
                    <Button
                        onClick={submitClicked}
                        variant="outlined"
                    >
                        ??????
                    </Button>
                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default recommendation3;