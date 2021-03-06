import React from 'react';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import HomeIcon from "@material-ui/icons/Home";
import ListItemText from "@material-ui/core/ListItemText";
import AssignmentIndIcon from "@material-ui/icons/AssignmentInd";
import InboxIcon from "@material-ui/icons/MoveToInbox";
import AccessibilityIcon from "@material-ui/icons/Accessibility";
import {useHistory, useLocation} from "react-router-dom";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";
import axios from 'axios'
import {makeStyles} from "@material-ui/core/styles";
import app from "../resources/application.json"

const useStyles = makeStyles((theme)=>({
    item :{
        fontSize:"25px"
    }
}))

const Menu = () =>{
    const classes = useStyles()
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
        axios.get(app.ip+'/users/' + location.state.email)
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
                    "email": location.state.email
                })
            }).catch(err=>{
                alert(err.response.data.error)
                history.push('/nalot')
        })
    }

    const myRecord = () =>{
        axios.get(app.ip+'/users/histories/'+location.state.email)
            .then(res=>{
                console.log(res.data)
                history.push('/nalot/myrecord',{
                    "key": location.state.key,
                    "email": location.state.email,
                    "data":res.data

                })
            }).catch(err=>{
            alert(err.response.data.error)
            history.push('/nalot')
        })

    }

    const recommendation = () =>{
        history.push('/nalot/recommendation_location',{
            "key": location.state.key,
            "email" : location.state.email,
        })

    }

    const logOut = () =>{
        axios.defaults.headers.common['Authorization'] = ""
        //window.history.pushState(null, null, '/nalot')
        history.push('/nalot')
    }

    return(
    <div
        style={{
            marginRight:'10px',
            height:window.outerHeight,
            position:'absolute',
        }}
    >
        <List className="List">
            <ListItem className="ListItem" button onClick={home}>
                <ListItemIcon ><HomeIcon/></ListItemIcon>
                <ListItemText classes={{primary: classes.item}} primary={'???'}/>
            </ListItem>
            <ListItem className="ListItem" button onClick={myInformation}>
                <ListItemIcon><AssignmentIndIcon /></ListItemIcon>
                <ListItemText classes={{primary: classes.item}} primary={'?????????'} />
            </ListItem>
            <ListItem className="ListItem" button onClick={myRecord}>
                <ListItemIcon><InboxIcon /></ListItemIcon>
                <ListItemText classes={{primary: classes.item}} primary={'??? ??????'} />
            </ListItem>
            <ListItem className="ListItem" button onClick={recommendation}>
                <ListItemIcon><AccessibilityIcon /></ListItemIcon>
                <ListItemText classes={{primary: classes.item}}primary={'?????? ??????'} />
            </ListItem>
            <ListItem className="ListItem" button onClick={logOut}>
                <ListItemIcon><ExitToAppIcon /></ListItemIcon>
                <ListItemText classes={{primary: classes.item}}primary={'????????????'} />
            </ListItem>
        </List>
    </div>
    )
}

export default Menu;