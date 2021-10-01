import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import kakaomap from "../Components/kakaomap";
import Location from "../Components/Location";

function recommendation_location(){

    return(
        <div>
            <Header/>
            <Menu/>
            <Location/>
            <div>
                <h1>지도위 마커를 클릭해 지역을 선택하세요</h1>
            </div>
            <div
                style={{
                    marginLeft:'40%'
                }}
            >
                {kakaomap()}
                <div id ="map" style={{width:"500px", height:"400px"}}/>
            </div>

        </div>

    )
}

export default recommendation_location;