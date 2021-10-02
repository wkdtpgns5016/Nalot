import React from 'react';
import Header from "../Components/Header"
import Menu from "../Components/Menu"
import Footer from "../Components/Footer"
import{useLocation} from "react-router-dom";
import icon from '../Images/icon.png'

function Main() {
    let location=useLocation()
    console.log('main'+ location.state.email)

    return (
        <div>
            <Header/>
            <Menu/>
            <div>
                <img src={icon} alt="icon"/>
            </div>
            <div
                style={{
                    fontSize:'20px'
                }}
            >
                <p>
                    Nalot은 날씨 별 의상 추천 웹서비스로서
                </p>
                <p>
                    기존의 의상추천 뿐만아니라 해당 추천 의상의
                    색상을 선택할 수 있으며,
                    <p>
                    선택한 색상에 대한
                    매치 색상을 추천해주어 간편하게 의상과 색상 매치를
                    조합해볼수 있습니다.
                    </p>
                </p>
                <p>
                    의상 추천 메뉴에서 날씨에 따른 의상 추천을 받아보고,
                </p>
                <p>
                    의상 색상과 매칭 색상을 정해보세요!
                </p>
            </div>
            <Footer/>
        </div>
    );
}

export default Main;