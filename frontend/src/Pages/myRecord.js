import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from'axios'

import {useLocation} from "react-router-dom";

function myRecord() {
    let arr =[]
    let data;
    const location = useLocation()

    function clicked(e) {
        console.log(e.target.value)
        axios.post('http://localhost:8080/users/'+location.state.email+'/'+e.target.value)
            .then(res=>{
                console.log(res.data)
            })
    }

    function renderData(){
        arr = location.state.data
        return arr.map(({userClothes, weather })=>{
            data = (userClothes.id).substring(0,8)
            return(
                <tr>
                    <td>
                        {data}
                    </td>
                    <td key={weather.temperature}>
                        {weather.temperature}
                    </td>
                    <td key={userClothes.clothes.name}>
                        {userClothes.clothes.name}
                    </td>
                    <td key={userClothes.color}>
                        {userClothes.color}
                    </td>
                    <td key={userClothes.colorMix}>
                        {userClothes.colorMix}
                    </td>
                    <td>
                        <button
                            id="button"
                            value={userClothes.id}
                            onClick={e=>clicked(e,"value")}
                        >상세보기</button>
                    </td>
                </tr>
            )
        })
    }
    return(
        <div>
            <Header/>
            <Menu/>
            <table border = "1">
                <tbody>
                <tr>
                    <th>날짜</th>
                    <th>온도</th>
                    <th>옷</th>
                    <th>사용자색</th>
                    <th>추천색</th>
                </tr>
                {renderData()}
                </tbody>
            </table>
        </div>
    )
}

export default myRecord;