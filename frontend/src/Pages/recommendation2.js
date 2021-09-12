import React from 'react'
import { useHistory, useLocation } from "react-router-dom";

import Header from "../Components/Header";
import Menu from "../Components/Menu";

function recommendation2(){
    const history = useHistory();
    const location = useLocation();


    const prevClicked = () =>{
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

            "current":location.state.current,
        })
    }

    const nextClicked = () =>{
        history.push('/nalot/recommendation3',{
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
                <span>선택한 의상</span>
                &nbsp;
                &nbsp;
                &nbsp;
                <span>톤온톤 매치</span>
            </div>
            <br></br>
            <div>
                <button>선택한 의상</button>
                &nbsp;
                &nbsp;
                &nbsp;
                <button>톤온톤 매치</button>
            </div>
            <br></br>
            <div>
                <span>선택한 색상</span>
                &nbsp;
                &nbsp;
                &nbsp;
                <span>톤인톤 매치</span>
            </div>
            <div>
                <button>선택한 색상</button>
                &nbsp;
                &nbsp;
                &nbsp;
                <button>톤인톤 매치</button>
            </div>
            <div>
                <button
                    onClick={prevClicked}
                >이전</button>
                <button
                    onClick={nextClicked}
                >다음</button>

            </div>
        </div>
    )
}

export default recommendation2;