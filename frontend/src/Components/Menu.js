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

const Menu = () =>{
    const history = useHistory();
    const location = useLocation();

    const home = () =>{
        history.push('/nalot/main',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,
        })
    }

    const myInformation = () =>{

        history.push('/nalot/myinformation',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,

        })
    }

    const myRecord = () =>{
        console.log('myRecord')
    }

    const recommendation = () =>{
        history.push('/nalot/recommendation_location',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,
        })
    }

    const weatherNow = () =>{
        console.log('weatherNow')
    }

    const logOut = () =>{
        console.log('logOut')
    }
    return(
        <List>
            <ListItem button onClick={home}>
                <ListItemIcon><HomeIcon/></ListItemIcon>
                <ListItemText primary={'홈'}/>
            </ListItem>
            <ListItem button onClick={myInformation}>
                <ListItemIcon><AssignmentIndIcon /></ListItemIcon>
                <ListItemText primary={'내정보'} />
            </ListItem>
            <ListItem button onClick={myRecord}>
                <ListItemIcon><InboxIcon /></ListItemIcon>
                <ListItemText primary={'내 기록'} />
            </ListItem>
            <ListItem button onClick={recommendation}>
                <ListItemIcon><AccessibilityIcon /></ListItemIcon>
                <ListItemText primary={'의상 추천'} />
            </ListItem>
            <ListItem button onClick={weatherNow}>
                <ListItemIcon><WbSunnyIcon /></ListItemIcon>
                <ListItemText primary={'현재 날씨'} />
            </ListItem>
            <ListItem button onClick={logOut}>
                <ListItemIcon><ExitToAppIcon /></ListItemIcon>
                <ListItemText primary={'로그아웃'} />
            </ListItem>
        </List>
    )
}

export default Menu;