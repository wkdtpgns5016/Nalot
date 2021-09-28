import React from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import Clothes from "../Components/Clothes"
import axios from "axios";
import Button from "@material-ui/core/Button";

function recommendation(){
    const history = useHistory();
    const location = useLocation();

    let arr = [];
    let clothes = null;
    let hex = '#f17013'
    let toneintone
    let toneontone
    let clothes_number = null;

    const prevClicked = () =>{
        history.push('/nalot/recommendation_location',{
            "key": location.state.key,
            "email":location.state.email,
            "current":location.state.temperature,
            "loc" : location.state.loc,
            "data" : location.state.data,
            "baseDate" : location.state.baseDate,
            "baseTime" : location.state.baseTime,
            "weathers_data" : location.state.weathers_data
        })
    }

    const nextClicked = () =>{
        let count = document.getElementsByName("radio-button").length

        for(let i = 0; i<count; i++){
            if(document.getElementsByName("radio-button")[i].checked === true){

                clothes = (document.getElementsByName("radio-button")[i].value)
                clothes_number = (document.getElementsByName("radio-button")[i].id)
            }
        }
        console.log(clothes_number, clothes)
        if(clothes === null){

        }else {
            console.log(clothes_number)
            axios.post('http://localhost:8080/colors/tone-on-tone-mixes',{
                "hexCode" : hex
            },{
                headers:{
                    'Content-Type':'application/json',
                    'Authorization':`${location.state.key}`
                }
            }).then(response=>{
                toneintone = response.data

                axios.post('http://localhost:8080/colors/tone-in-tone-mixes',{
                    "hexCode":hex
                },{
                    headers:{
                        'Content-Type':'application/json',
                        'Authorization':`${location.state.key}`
                    }
                }).then(res=>{
                    toneontone = res.data
                    //console.log(toneontone)
                    //console.log(location.state.baseDate, location.state.baseTime)
                    history.push('/nalot/recommendation2',
                        {
                            "key": location.state.key,
                            "email": location.state.email,

                            "current":location.state.current,

                            "data" : location.state.data,
                            "clothes" : clothes,
                            "clothes_number" : clothes_number,
                            "toneintone" : toneintone,
                            "toneontone" : toneontone,

                            "baseDate" : location.state.baseDate,
                            "baseTime" : location.state.baseTime,
                        })
                })
            })

        }
    }

    function renderData(){
        arr = location.state.data

        return arr.map(({name, id})=>{
            return(
                <label>
                    <input
                        type="radio"
                        name="radio-button"
                        value={name}
                        id = {id}
                    />
                    {name}
                </label>
            )
        })

    }

    return(

        <div

        >
            <Header/>
            <Menu/>
            <Clothes/>
            <div>
                현재 <h2>{location.state.loc}</h2>의 기온은 <h2>{location.state.current}</h2>도입니다.
            </div>
            <div
                style={{

                }}
            >
                추천 의상은 아래와 같습니다
                <div
                >
                    {renderData()}
                </div>
            </div>
            <Button
                onClick={prevClicked}
                variant="outlined"
                >이전</Button>
            <Button
                onClick={nextClicked}
                variant="outlined"
            >다음</Button>

        </div>


    );
}

export default recommendation;