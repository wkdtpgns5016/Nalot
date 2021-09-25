import React from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from "axios";
import Button from "@material-ui/core/Button";
import {FormControlLabel, Radio, RadioGroup} from "@material-ui/core";

function recommendation(){
    const history = useHistory();
    const location = useLocation();

    let arr = [];
    let clothes = null;
    let hex = '#f17013'
    let toneintone
    let toneontone
    let clothes_number = null;


    const nextClicked = () =>{
        let count = document.getElementsByName("radio-button").length

        for(let i = 0; i<count; i++){
            if(document.getElementsByName("radio-button")[i].checked === true){

                clothes = (document.getElementsByName("radio-button")[i].value)
                clothes_number = (document.getElementsByName("radio-button")[i].id)
            }
        }
        console.log(clothes_number)
        if(clothes === null){

        }else {
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

                <FormControlLabel
                    name="radio-button"
                    control={<Radio color="primary"/>}
                    value={name}
                    id = {id}
                    label={name}
                    style={{

                    }}
                />


            )
        })

    }

    return(

        <div

        >
            <Header/>
            <Menu/>
            <div>
                현재 <h2>{location.state.loc}</h2>의 기온의 날씨는 <h2>{location.state.current}</h2>도입니다.
            </div>
            <div
                style={{

                }}
            >
                추천 의상은 아래와 같습니다
                <div
                    style={{
                        marginLeft:'45%'
                    }}
                >
                    <RadioGroup
                        row={true}
                    >
                        {renderData()}
                    </RadioGroup>
                </div>
            </div>
            <Button
                onClick={nextClicked}
                variant="outlined"
            >다음</Button>

        </div>


    );
}

export default recommendation;