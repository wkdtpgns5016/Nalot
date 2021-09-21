import React from 'react'

import {useHistory, useLocation} from "react-router-dom";
import Header from "../Components/Header"
import Menu from "../Components/Menu";
import axios from 'axios'

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
            <tr>
                <td>
                    {data}
                </td>
                <td key={arr.weather.temperature}>
                    {arr.weather.temperature}
                </td>
                <td key={arr.userClothes.clothes.name}>
                    {arr.userClothes.clothes.name}
                </td>
                <td>
                    <button style=
                        {
                            {
                                background: arr.userClothes.color
                            }
                        }
                    >
                        {arr.userClothes.color}
                    </button>
                </td>
                <td>
                    <button style={
                        {
                            background: arr.userClothes.colorMix
                        }
                    }>
                    {arr.userClothes.colorMix}
                    </button>
                </td>
                <td>
                    <button
                        onClick={deleteClicked}
                    >
                        삭제
                    </button>
                </td>
            </tr>
        )

    }
    return(
        <div>
            <Header/>
            <Menu/>
            <div>
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
                <button
                    onClick={prevClicked}
                >
                    뒤로
                </button>
            </div>
        </div>
    )
}

export default myRecord_detail;