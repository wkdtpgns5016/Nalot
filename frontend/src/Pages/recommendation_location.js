import React from 'react'
import axios from 'axios'
import {useHistory, useLocation} from "react-router-dom"
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import {InputLabel, MenuItem, Select} from "@material-ui/core";
import Button from "@material-ui/core/Button";

function recommendation_location(){
    const history = useHistory();
    const location = useLocation();
    let data;

    const [loc, setLoc] = React.useState('서울특별시');


    //console.log(location.state.data)
    console.log('loc'+ location.state.email)

    const submitLocation = () =>{
        //console.log(loc)

        axios.post('http://localhost:8080/weathers/forecasts',{
            "location" : loc

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

                    "loc" : loc,

                    "data" : data,

                    "baseDate" : response.data.baseDate,
                    "baseTime" : response.data.baseTime

                })
            })

        })
    }

    const valueChanged = (event) =>{
        setLoc(event.target.value)
        //console.log(loc)
    }

    return(
        <div>
            <Header/>
            <Menu/>
            <div>
                <h2>지역 설정</h2>
            </div>
            <div>
                <Select
                    defaultValue="서울특별시"

                    id="location" onChange={valueChanged}>
                    <MenuItem value="서울특별시" defaultValue={true}>서울특별시</MenuItem>
                    <MenuItem value="부산광역시">부산광역시</MenuItem>
                    <MenuItem value="대구광역시">대구광역시</MenuItem>
                    <MenuItem value="인천광역시">인천광역시</MenuItem>
                    <MenuItem value="광주광역시">광주광역시</MenuItem>
                    <MenuItem value="울산광역시">울산광역시</MenuItem>
                    <MenuItem value="세종특별자치시">세종특별자치시</MenuItem>
                    <MenuItem value="경기도">경기도</MenuItem>
                    <MenuItem value="강원도">강원도</MenuItem>
                    <MenuItem value="충청북도">충청북도</MenuItem>
                    <MenuItem value="충청남도">충청남도</MenuItem>
                    <MenuItem value="전라북도">전라북도</MenuItem>
                    <MenuItem value="전라남도">전라남도</MenuItem>
                    <MenuItem value="경상북도">경상북도</MenuItem>
                    <MenuItem value="경상남도">경상남도</MenuItem>
                    <MenuItem value="제주특별자치도">제주특별자치도</MenuItem>
                    <MenuItem value="이어도">이어도</MenuItem>
                </Select>
            </div>
            <div>
                <Button
                    onClick={submitLocation}
                    variant="outlined"
                >옷 추천받기</Button>
            </div>
        </div>

    )
}

export default recommendation_location;