import React from 'react';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import HomeIcon from "@material-ui/icons/Home";
import ListItemText from "@material-ui/core/ListItemText";
import AssignmentIndIcon from "@material-ui/icons/AssignmentInd";
import InboxIcon from "@material-ui/icons/MoveToInbox";
import AccessibilityIcon from "@material-ui/icons/Accessibility";
import WbSunnyIcon from "@material-ui/icons/WbSunny";
import {useHistory, useLocation} from "react-router-dom";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";
import axios from 'axios'

const Menu = () =>{
    const history = useHistory();
    const location = useLocation();

    const home = () =>{
        history.push('/nalot/main',{
            "key": location.state.key,
            "email" : location.state.email
        })
    }

    const myInformation = () =>{
        //console.log(location.state)
        //axios.defaults.headers.common['Authorization'] = location.state.key
        axios.get('http://localhost:8080/users/' + location.state.email)
            .then(res=>{
                history.push('/nalot/myinformation',{
                    "key": location.state.key,
                    "name": res.data.name,
                    "id": location.state.email,
                    "gender": res.data.gender,
                    "zone_code": res.data.zoneCode,
                    "address_basic": res.data.addressBasic,
                    "address_detail": res.data.addressDetail,
                    "address_ground_number": res.data.addressGroundNumber,
                    "birth": res.data.birth,

                })
            })

    }

    const myRecord = () =>{
        console.log('myRecord')
    }

    const recommendation = () =>{
        //console.log(location.state.email)
        history.push('/nalot/recommendation_location',{
            "key": location.state.key,
            "email" : location.state.email
        })
    }

    const weatherNow = () =>{
        console.log('weatherNow')
    }

    const logOut = () =>{
        console.log('logOut')
    }
    return(
    <div>
        <List className="List">
            <ListItem className="ListItem" button onClick={home}>
                <ListItemIcon ><HomeIcon/></ListItemIcon>
                <ListItemText className="ItemText" primary={'홈'}/>
            </ListItem>
            <ListItem className="ListItem" button onClick={myInformation}>
                <ListItemIcon><AssignmentIndIcon /></ListItemIcon>
                <ListItemText primary={'내정보'} />
            </ListItem>
            <ListItem className="ListItem" button onClick={myRecord}>
                <ListItemIcon><InboxIcon /></ListItemIcon>
                <ListItemText primary={'내 기록'} />
            </ListItem>
            <ListItem className="ListItem" button onClick={recommendation}>
                <ListItemIcon><AccessibilityIcon /></ListItemIcon>
                <ListItemText primary={'의상 추천'} />
            </ListItem>
            <ListItem className="ListItem" button onClick={weatherNow}>
                <ListItemIcon><WbSunnyIcon /></ListItemIcon>
                <ListItemText primary={'현재 날씨'} />
            </ListItem>
            <ListItem className="ListItem" button onClick={logOut}>
                <ListItemIcon><ExitToAppIcon /></ListItemIcon>
                <ListItemText primary={'로그아웃'} />
            </ListItem>
        </List>
    </div>
    )
}

export default Menu;