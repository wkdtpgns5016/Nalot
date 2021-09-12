import React from 'react'

import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";

function recommendation(){
    const history = useHistory();
    const location = useLocation();

    const nextClicked = () =>{
        history.push('/nalot/recommendation2',
            {
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
                현재 {location.state.loc}의 기온의 날씨는 {location.state.current}도입니다.
            </div>
            <div>
                추천 의상은 아래와 같습니다
                <div>
                    <label>
                        <input
                            type="radio"
                            name="react-tips"
                            value="option1"
                            defaultChecked={true}
                            className="form-check-input"
                        />
                        Option 1
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="react-tips"
                            value="option2"
                            className="form-check-input"
                        />
                        Option 2
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="react-tips"
                            value="option3"
                            className="form-check-input"
                        />
                        Option 3
                    </label>
                </div>
            </div>
            <button
                onClick={nextClicked}
            >다음</button>

        </div>


    );
}

export default recommendation;