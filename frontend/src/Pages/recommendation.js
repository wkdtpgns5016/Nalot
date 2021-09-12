import React, {useState} from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from "axios";

function recommendation(){
    const history = useHistory();
    const location = useLocation();

    let arr = [];

    const nextClicked = () =>{
        history.push('/nalot/recommendation2',
            {
                "key": location.state.key,
                "email": location.state.email,

                "current":location.state.current,

                "data" : location.state.data
            })
    }


    function renderData(){
        arr = location.state.data
        return arr.map(({name})=>{
            return(
                <label>
                    <input
                        type="radio"
                        name="react-tips"
                        value="option1"
                        defaultChecked={true}
                        className="form-check-input"
                    />
                    {name}
                </label>
            )
        })

    }

    console.log(location.state.data)


    return(

        <div>
            <Header/>
            <Menu/>
            <div>
                현재 {location.state.loc}의 기온의 날씨는 {location.state.current}도입니다.
            </div>
            <div>
                추천 의상은 아래와 같습니다
                <div>
                    {renderData()}
                </div>
            </div>
            <button
                onClick={nextClicked}
            >다음</button>

        </div>


    );
}

export default recommendation;