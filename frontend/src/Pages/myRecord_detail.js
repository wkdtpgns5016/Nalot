import React from 'react'

import {useHistory, useLocation} from "react-router-dom";
import Header from "../Components/Header"
import Menu from "../Components/Menu";
import axios from 'axios'
import Button from "@material-ui/core/Button";

function myRecord_detail(){
    const location = useLocation()
    const history = useHistory()
    let data
    let arr

    const prevClicked = () =>{
        history.push('/nalot/myrecord',{
            "key" : location.state.key,
            "email" : location.state.email,
            "data" : location.state.data
        })
    }

    const deleteClicked=()=>{
        axios.delete('http://localhost:8080/users/histories/'+location.state.email+'/'+location.state.value)
            .then(res=>{
                alert(res.data.message)
                history.push('/nalot/main',{
                    "key":location.state.key,
                    "email":location.state.email,

                })
            })
            .catch(error=>{
                alert(error)
            })
    }

    function renderData(){
        arr = location.state.detailed_data
        data = (arr.userClothes.id).substring(0,8)
        return(
            <div style={
                {
                    backgroundColor: 'rgb(244, 244, 244)',
                    width: '300px',
                    height: '200px',
                    marginLeft: '42%',
                }
            }
            >
                <p>
                    {data}
                </p>
                <p key={arr.userClothes.clothes.name}>
                    {arr.userClothes.clothes.name}
                </p>
                <p key={arr.weather.temperature}>
                    {arr.weather.temperature}
                </p>
                <p>
                    <button style=
                        {
                            {
                                background: arr.userClothes.color
                            }
                        }
                    >
                        {arr.userClothes.color}
                    </button>
                </p>
                <p>
                    <button style={
                        {
                            background: arr.userClothes.colorMix
                        }
                    }>
                    {arr.userClothes.colorMix}
                    </button>
                </p>
            </div>
        )

    }
    return(
        <div>
            <Header/>
            <Menu/>
            <div>
                {renderData()}
                <Button
                    onClick={deleteClicked}
                    variant="contained"
                >
                    삭제
                </Button>
                <Button
                    onClick={prevClicked}
                    variant="contained"
                >
                    뒤로
                </Button>
            </div>
        </div>
    )
}

export default myRecord_detail;