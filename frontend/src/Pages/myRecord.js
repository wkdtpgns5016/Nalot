import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";

import {useLocation} from "react-router-dom";

function myRecord() {
    let arr =[]
    const location = useLocation()
    function renderData(){
        arr = location.state.data
        return arr.map(({userClothes, weather })=>{
            console.log(userClothes, weather)
            return(
                <tr>
                    <td key = {userClothes.id}>
                        {userClothes.id}
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
                        <button>상세보기</button>
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