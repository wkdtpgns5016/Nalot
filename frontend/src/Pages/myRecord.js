import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from'axios'

import {useHistory, useLocation} from "react-router-dom";
import Button from "@material-ui/core/Button";

function myRecord() {
    let arr =[]
    let data;
    const history = useHistory()
    const location = useLocation()

    function clicked(e) {
        console.log(e.target.value)
        axios.get('http://localhost:8080/users/histories/'+location.state.email+'/'+e.target.value)
            .then(res=>{
                console.log(res.data)
                history.push('/nalot/myrecord_detail',
                    {
                        "key" : location.state.key,
                        "email" : location.state.email,
                        "detailed_data" : res.data,
                        "data" : location.state.data,
                        "value" : e.target.value
                    }
                )
            })
    }

    function renderData(){
        arr = location.state.data
        return arr.map(({userClothes, weather, id })=>{
            data = (userClothes.id).substring(0,8)
            return(
                <div style={
                    {
                        backgroundColor: 'rgb(244, 244, 244)',
                        width: '300px',
                        height: '180px',
                        marginLeft: '40%',

                    }
                }>
                    <p align="right">
                        {data}
                    </p>
                    <p align="left" key={userClothes.clothes.name}>
                        <h2>{userClothes.clothes.name}</h2>
                    </p>
                    <p align="right" key={weather.temperature}>
                        {weather.temperature}도
                    </p>
                    <p>
                        <Button
                            id="button"
                            value={id}
                            onClick={e=>clicked(e,"value")}
                            variant="contained"
                        >상세보기</Button>
                    </p>
                </div>
            )
        })
    }
    return(
        <div>
            <Header/>
            <Menu/>
            <div className="container-fluid">
                <div className="row">
                    {renderData()}
                </div>
            </div>
        </div>
    )
}

export default myRecord;