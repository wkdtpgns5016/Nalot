import React from 'react';
import Header from "../Components/Header"
import Menu from "../Components/Menu"


import {useLocation} from "react-router-dom";

function myInformation(){
    const location = useLocation()
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

        </div>
    )
}

export default myInformation