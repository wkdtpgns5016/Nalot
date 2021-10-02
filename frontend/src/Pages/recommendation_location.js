import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import kakaomap from "../Components/kakaomap";
import Location from "../Components/Location";
import Footer from "../Components/Footer";

function recommendation_location(){


    return(
        <div>
            <Header/>
            <Menu/>
            <h1>의상 추천</h1>
            <Location/>
            <div>
                <h2>지도위 마커를 클릭해 지역을 선택하세요</h2>
            </div>
            <div
            >
                {kakaomap()}
                <div id ="map" style={{width:"800px", height:"600px",marginLeft:'30%'}}/>
            </div>
            <Footer/>

        </div>

    )
}

export default recommendation_location;