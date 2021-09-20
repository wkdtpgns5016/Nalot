import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from'axios'

import {useHistory, useLocation} from "react-router-dom";

function myRecord() {
    let arr =[]
    let data;
    const history = useHistory()
    const location = useLocation()

    function clicked(e) {
        console.log(e.target.value)
        axios.get('http://localhost:8080/users/histories/'+location.state.email+'/'+'1')
            .then(res=>{
                console.log(res.data)
                history.push('/nalot/myrecord_detail',
                    {
                        "key" : location.state.key,
                        "email" : location.state.email,
                        "detailed_data" : res.data,
                        "data" : location.state.data
                    }

                )
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
                </tr>
                {renderData()}
                </tbody>
            </table>
        </div>
    )
}

export default myRecord;