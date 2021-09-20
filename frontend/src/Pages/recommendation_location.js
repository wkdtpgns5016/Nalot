import React from 'react'
import axios from 'axios'
import {useHistory, useLocation} from "react-router-dom"
import Header from "../Components/Header";
import Menu from "../Components/Menu";

function recommendation_location(){
    const history = useHistory();
    const location = useLocation();
    let data;


    console.log(location.state.data)

    const submitLocation = () =>{
        let s = document.getElementById("location")
        let v = s.options[s.selectedIndex].value
        console.log(v)

        axios.post('http://localhost:8080/weathers/forecasts',{
            "location" : v

        }, {
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`

            }
        }).then(response=>{
            console.log(response.data.baseDate)

            axios.post('http://localhost:8080/clothes/recommendations',{
                "id":null, "temperature":response.data.temperature, "userId":null,
                "baseDate":null, "baseTime":null
            },{
                headers:{
                    'Content-Type':'application/json',
                    'Authorization':`${location.state.key}`
                }
            }).then(res=>{

                data = res.data
                //console.log(res.data)

                history.push('/nalot/recommendation',{
                    "key": location.state.key,
                    "email":location.state.email,

                    "current":response.data.temperature,

                    "loc" : v,

                    "data" : data,

                    "baseDate" : response.data.baseDate,
                    "baseTime" : response.data.baseTime

                })
            })

        })
    }

    const valueChanged = () =>{
        let s = document.getElementById("location")
        let v  = s.options[s.selectedIndex].value
    }
    return(
        <div>
            <Header/>
            <Menu/>
            <div>
                지역 설정
            </div>
            <div>
                <select id="location" onChange={valueChanged}>
                    <option value="서울특별시" defaultValue={true}>서울특별시</option>
                    <option value="부산광역시">부산광역시</option>
                    <option value="대구광역시">대구광역시</option>
                    <option value="인천광역시">인천광역시</option>
                    <option value="광주광역시">광주광역시</option>
                    <option value="울산광역시">울산광역시</option>
                    <option value="세종특별자치시">세종특별자치시</option>
                    <option value="경기도">경기도</option>
                    <option value="강원도">강원도</option>
                    <option value="충청북도">충청북도</option>
                    <option value="충청남도">충청남도</option>
                    <option value="전라북도">전라북도</option>
                    <option value="전라남도">전라남도</option>
                    <option value="경상북도">경상북도</option>
                    <option value="경상남도">경상남도</option>
                    <option value="제주특별자치도">제주특별자치도</option>
                    <option value="이어도">이어도</option>
                </select>
            </div>
            <div>
                <button
                    onClick={submitLocation}
                >옷 추천받기</button>
            </div>
        </div>

    )
}

export default recommendation_location;