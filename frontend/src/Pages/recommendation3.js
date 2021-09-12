import React from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";


function recommendation3(){

    const history = useHistory();
    const location = useLocation();

    const submitClicked = () =>{

        history.push('/nalot/recommendation_location',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,

        })
    }
    const prevClicked = () =>{
        history.push('/nalot/recommendation2',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,

            "current":location.state.current,
        })
    }

    return(
        <div>
            <Header/>
            <Menu/>
            <div>
                선택한 의상
                의상 색상
                추천색상배치
            </div>
            <div>
                <button
                    onClick={prevClicked}
                >이전</button>
                <button
                    onClick={submitClicked}
                >
                    저장
                </button>
            </div>
        </div>
    )
}

export default recommendation3;