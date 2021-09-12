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
            "email": location.state.email,

        })
    }
    const prevClicked = () =>{
        history.push('/nalot/recommendation2',{
            "email": location.state.email,
            "key": location.state.key,
            "current":location.state.current,
            "data":location.state.data
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