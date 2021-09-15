import React from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from "axios";

function recommendation(){
    const history = useHistory();
    const location = useLocation();

    let arr = [];
    let clothes = null;
    let hex = '#f17013'
    let toneintone
    let toneontone

    const nextClicked = () =>{
        let count = document.getElementsByName("radio-button").length

        for(let i = 0; i<count; i++){
            if(document.getElementsByName("radio-button")[i].checked === true){

                clothes = (document.getElementsByName("radio-button")[i].value)
            }
        }
        console.log(clothes)
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
                    history.push('/nalot/recommendation2',
                        {
                            "key": location.state.key,
                            "email": location.state.email,

                            "current":location.state.current,

                            "data" : location.state.data,
                            "clothes" : clothes,
                            "toneintone" : toneintone,
                            "toneontone" : toneontone
                        })
                })
            })

        }
    }

    function renderData(){
        arr = location.state.data
        return arr.map(({name})=>{
            return(
                <label>
                    <input
                        type="radio"
                        name="radio-button"
                        value={name}
                        //defaultChecked={true}
                    />
                    {name}
                </label>
            )
        })

    }

    //console.log(location.state.data)


    return(

        <div>
            <Header/>
            <Menu/>
            <div>
                현재 {location.state.loc}의 기온의 날씨는 {location.state.current}도입니다.
            </div>
            <div>
                추천 의상은 아래와 같습니다
                <div>
                    {renderData()}
                </div>
            </div>
            <button
                onClick={nextClicked}
            >다음</button>

        </div>


    );
}

export default recommendation;