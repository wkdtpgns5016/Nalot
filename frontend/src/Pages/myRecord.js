import React from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from'axios'

import {useHistory, useLocation} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {Divider, ListItem, ListItemAvatar, ListItemText} from "@material-ui/core";
import List from "@material-ui/core/List";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";

function myRecord() {
    let arr =[]
    let data;
    let year
    let month
    let day
    const history = useHistory()
    const location = useLocation()
    console.log('myrecord'+ location.state.email)

    function clicked(e) {
        console.log(e.currentTarget.value)
        axios.get('http://localhost:8080/users/histories/'+location.state.email+'/'+e.currentTarget.value)
            .then(res=>{
                console.log(res.data)
                history.push('/nalot/myrecord_detail',
                    {
                        "key" : location.state.key,
                        "email" : location.state.email,
                        "detailed_data" : res.data,
                        "data" : location.state.data,
                        "value" : e.target.value
                    }
                )
            })
    }

    function renderData(){
        arr = location.state.data
        console.log(arr)
        return arr.map(({userClothes, weather, id })=>{
            data = (userClothes.id).substring(0,8)
            //return 안은 반복문
            return(
                <div style={
                    {
                        marginLeft:'200px',
                        backgroundColor:'rgb(244,244,244)',
                        width:'600px',
                        marginBottom:'10px',
                        marginRight:'200px'
                    }
                }>
                    <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                        <ListItem alignItems="flex-start">
                            <ListItemAvatar>
                                <Avatar alt="Remy Sharp" src="/static/images/avatar/1.jpg" />
                            </ListItemAvatar>
                            <ListItemText
                                primary={data}
                                secondary={
                                    <React.Fragment>
                                        <Typography
                                            sx={{ display: 'inline' }}
                                            component="span"
                                            variant="body2"
                                            color="text.primary"
                                        >
                                            {userClothes.clothes.name}
                                        </Typography>
                                        {" - " + weather.temperature}도
                                    </React.Fragment>

                                }
                            />
                            <Button
                                style={{maxWidth: '60px', maxHeight: '30px', minWidth: '60px', minHeight: '30px'}}
                                id="button"
                                value={id}
                                onClick={e=>clicked(e,"value")}
                                variant="outlined"
                            >보기</Button>
                        </ListItem>
                    </List>
                    <Divider
                        style={{
                            marginTop:"3px",
                            marginBottom:"3px"
                        }}
                        variant="middle"
                    ></Divider>
                </div>

            )
        })
    }
    //renderdata전은 반복문 아님
    return(
        <div>
            <Header/>
            <Menu/>
            <header>
                <h1>내기록</h1>
            </header>

            {renderData()}
        </div>
    )
}

export default myRecord;