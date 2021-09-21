import React from 'react';
import Header from "../Components/Header"
import Menu from "../Components/Menu"
import axios from 'axios'


import {useLocation, useHistory} from "react-router-dom";

function myInformation(){
    const location = useLocation()
    const history = useHistory()

    const fixClicked=()=>{
        history.push('/nalot/myinformation_fix',{
            "key" : location.state.key,
            "name" : location.state.name,
            "id" : location.state.email,
            "gender" : location.state.gender,
            "zone_code" : location.state.zone_code,
            "address_basic" : location.state.address_basic,
            "address_detail" : location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth" : location.state.birth,
            "email" : location.state.email,
        })
    }
    const deleteClicked=()=>{
        axios.delete('http://localhost:8080/users/'+location.state.email)
            .then(res=>{
                if(res.status===201){
                    alert(res.data.message)
                    history.push('/nalot')
                }
                }
            ).catch(error=>{
                alert(error.response.data.errorMessage)
        })
    }

    return(
        <div>
            <Header/>
            <Menu/>
            <div>
                이름 {location.state.name}
                <div>
                    이메일 {location.state.id}
                </div>
                <div>
                    성별 {location.state.gender}
                </div>
                <div>
                    zone_code {location.state.zone_code}
                </div>
                <div>
                    address_basic {location.state.address_basic}
                </div>
                <div>
                    address_detail {location.state.address_detail}
                </div>
                <div>
                    address_ground_number {location.state.address_ground_number}
                </div>
                <div>
                    birth {location.state.birth}
                </div>
            </div>
            <div>
                <button
                    onClick={fixClicked}
                >
                    정보 수정
                </button>
                <button
                    onClick={deleteClicked}
                >
                    회원 탈퇴
                </button>
            </div>

        </div>
    )
}

export default myInformation