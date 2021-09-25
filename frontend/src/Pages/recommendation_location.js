import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import kakaomap from "../Components/kakaomap";

function recommendation_location(){

    return(
        <div>
            <Header/>
            <Menu/>
            <div>
                <h2>지역 설정</h2>
            </div>
            <div
                style={{
                    marginLeft:'45%'
                }}
            >
                {kakaomap()}
                <div id ="map" style={{width:"500px", height:"400px"}}/>
            </div>
        </div>

    )
}

export default recommendation_location;