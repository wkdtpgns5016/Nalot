import React from 'react'
import axios from 'axios'
import {useHistory, useLocation} from "react-router-dom"
import Header from "../Components/Header";
import Menu from "../Components/Menu";

function recommendation_location(){
    const history = useHistory();
    const location = useLocation();
    let nx, ny, loc;

    nx = "60"
    ny = "127"
    loc = "서울특별시"

    const dateNow = () =>{
        let date = new Date();
        let year = date.getFullYear();
        let month = ("0" + (1 + date.getMonth())).slice(-2);
        let day = ("0" + date.getDate()).slice(-2);

        return year + month + day;
    }

    const hoursNow = () =>{
        let date = new Date();

        let hours = date.getHours();
        console.log((hours-1+'00'))
        //조건문 사용해서 02일 00시 -> 01시 23시로 바꿀것
        //매시 30분 마다 업뎃 -> getMinutes사용해서 30분ㅇ ㅣ후면 아워리턴 이전이면 아ㅜ어 -1 리턴
        return (hours-1) +'00';
    }

    const submitLocation = () =>{
        let s = document.getElementById("location")
        let v = s.options[s.selectedIndex].value
        console.log(v)
        if (v === "seoul"){
            nx = "60"
            ny = "127"
            loc = "서울특별시"
        }else if (v === "busan"){
            nx = "98"
            ny = "76"
            loc = "부산광역시"
        }else if (v === "daegu"){
            nx = "89"
            ny = "90"
            loc = "대구광역시"
        }else{
            nx = "60"
            ny = "127"
            loc = "서울특별시"
        }

        axios.post('http://localhost:8080/weathers/forecasts',{
            "date":dateNow(), "time":"0500","nx":nx , "ny": ny

        }, {
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`

            }
        }).then(response=>{
            history.push('/nalot/recommendation',{
                "key": location.state.key,
                "name": location.state.name,
                "id": location.state.id,
                "gender": location.state.gender,
                "zone_code": location.state.zone_code,
                "address_basic": location.state.address_basic,
                "address_detail": location.state.address_detail,
                "address_ground_number": location.state.address_ground_number,
                "birth": location.state.birth,

                "current":response.data.temperature,

                "nx" : nx,
                "ny" : ny,
                "loc" : loc

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
                    <option value="seoul" defaultValue={true}>서울특별시</option>
                    <option value="busan">부산광역시</option>
                    <option value="daegu">대구광역시</option>
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