import React, {useEffect} from 'react'
import axios from "axios";
import {useHistory, useLocation} from "react-router-dom";

const kakaomap = () =>{
    const {kakao} = window;
    const history = useHistory();
    const location = useLocation();
    let data;

    useEffect(()=>{
        let container = document.getElementById('map');
        let options = {
            center: new kakao.maps.LatLng(37.56667, 126.97806),
            level: 13
        };
        let map = new kakao.maps.Map(container, options);

        let zoomControl = new kakao.maps.ZoomControl()
        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT)

        //xml 파일로 변환?
        let positions =[
            {
                title: '서울특별시',
                latlng: new kakao.maps.LatLng(37.56, 126.97)
            },
            {
                title: '부산광역시',
                latlng: new kakao.maps.LatLng(35.17, 129.07)
            },
            {
                title: '대구광역시',
                latlng: new kakao.maps.LatLng(35.87, 128.60)
            },
            {
                title: '인천광역시',
                latlng: new kakao.maps.LatLng(37.27, 126.70)
            },
            {
                title: '광주광역시',
                latlng: new kakao.maps.LatLng(35.09, 126.85)
            },
            {
                title: '울산광역시',
                latlng: new kakao.maps.LatLng(35.53, 129.31)
            },
            {
                title: '세종특별자치시',
                latlng: new kakao.maps.LatLng(36.29, 127.16)
            },
            {
                title: '제주특별자치도',
                latlng: new kakao.maps.LatLng(33.50, 126.51)
            },



        ]

        let imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"

        for(let i = 0; i<positions.length; i++){
            let imageSize = new kakao.maps.Size(24,35)

            let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize)

            let marker = new kakao.maps.Marker({
                map: map,
                position: positions[i].latlng,
                title : positions[i].title,
                image: markerImage,
                clickable: true
            })
            let iwContent = positions[i].title,
                iwRemovable= true;

            let infowindow = new kakao.maps.InfoWindow({
                content: iwContent,
                removable: iwRemovable

            })
            kakao.maps.event.addListener(marker, 'click', function(){
                infowindow.open(map, marker)
                axios.post('http://localhost:8080/weathers/forecasts',{
                    "location" : marker.getTitle()

                }, {
                    headers:{
                        'Content-Type':'application/json',
                        'Authorization':`${location.state.key}`

                    }
                }).then(response=>{
                    console.log(response.data.baseDate)

                    axios.post('http://localhost:8080/clothes/recommendations',{
                        "id":null, "temperature":response.data.temperature, "userId":null,
                        "baseDate":null, "baseTime":null
                    },{
                        headers:{
                            'Content-Type':'application/json',
                            'Authorization':`${location.state.key}`
                        }
                    }).then(res=>{

                        data = res.data
                        //console.log(res.data)

                        history.push('/nalot/recommendation',{
                            "key": location.state.key,
                            "email":location.state.email,
                            "current":response.data.temperature,
                            "loc" : marker.getTitle(),
                            "data" : data,
                            "baseDate" : response.data.baseDate,
                            "baseTime" : response.data.baseTime

                        })
                    })

                })
            })
        }



    }, [])
}

export default kakaomap;