import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import kakaomap from "../Components/kakaomap";
import Location from "../Components/Location";
import Button from "@material-ui/core/Button";

function recommendation_location(){

    return(
        <div>
            <Header/>
            <Menu/>
            <Location/>
            <div
                style={{
                    marginLeft:'45%'
                }}
            >
                {kakaomap()}
                <div id ="map" style={{width:"500px", height:"400px"}}/>
            </div>
           <div>
                <Button
                    variant="outlined"
                >마커를 클릭하세요</Button>
            </div>
        </div>

    )
}

export default recommendation_location;