import React from 'react'

import {useHistory, useLocation} from "react-router-dom";
import Header from "../Components/Header"
import Menu from "../Components/Menu";
import axios from 'axios'
import Button from "@material-ui/core/Button";
import {Avatar, ListItem, ListItemAvatar, ListItemText, Typography} from "@material-ui/core";
import List from "@material-ui/core/List";

function myRecord_detail(){
    const location = useLocation()
    const history = useHistory()
    let data
    let year, month, day
    let arr

    const prevClicked = () =>{
        history.push('/nalot/myrecord',{
            "key" : location.state.key,
            "email" : location.state.email,
            "data" : location.state.data
        })
    }


    const deleteClicked=()=>{
        axios.delete('http://localhost:8080/users/histories/'+location.state.email+'/'+location.state.value)
            .then(res=>{
                alert(res.data.message)
                axios.get('http://localhost:8080/users/histories/'+location.state.email)
                    .then(response=>{
                        console.log(response.data)
                        history.push('/nalot/myrecord',{
                            "key": location.state.key,
                            "email": location.state.email,
                            "data":response.data

                        })
                    })
            })
            .catch(error=>{
                alert(error)
            })
    }

    function renderData(){
        arr = location.state.detailed_data
        data = (arr.userClothes.id).substring(0,8)
        year = data.substring(0,4)+"년"
        month = data.substring(4,6)+"월"
        day = data.substring(6,8)+"일"
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
                <List sx={{width:'100%', maxWidth: 360, bgColor:'background.paper'}}>
                    <ListItem alignItems="flex-start">
                        <ListItemAvatar>
                            <Avatar alt="Remy Sharp"/>
                        </ListItemAvatar>
                        <ListItemText
                            primary={year +" " +month +" " + day}
                            secondary={
                                <React.Fragment>
                                    <Typography
                                        sx={{display:'inline'}}
                                        component="span"
                                        variant="body2"
                                        color="text.primary"
                                        >
                                        {arr.userClothes.clothes.name}
                                    </Typography>
                                    {" - " + arr.weather.temperature+"°C"}
                                </React.Fragment>
                            }
                            />
                        <div
                            style={{
                                marginRight:"10px"
                            }}
                        >
                            선택색상
                            <p>
                                <Button style=
                                            {
                                                {
                                                    background: arr.userClothes.color,
                                                    maxWidth: '60px', maxHeight: '30px', minWidth: '60px', minHeight: '30px'
                                                }
                                            }
                                        variant="outlined"
                                >

                                </Button>
                            </p>

                        </div>
                        <div>
                            추천색상
                            <p>
                                <Button style={
                                    {
                                        background: arr.userClothes.colorMix,
                                        maxWidth: '60px', maxHeight: '30px', minWidth: '60px', minHeight: '30px'
                                    }

                                }
                                        variant="outlined"
                                >

                                </Button>
                            </p>

                        </div>
                    </ListItem>
                </List>
            </div>
        )

    }
    return(
        <div>
            <Header/>
            <Menu/>
            <h1>상세기록</h1>
            <div>
                {renderData()}
                <Button
                    onClick={deleteClicked}
                    variant="outlined"
                >
                    삭제
                </Button>
                <Button
                    onClick={prevClicked}
                    variant="outlined"
                >
                    뒤로
                </Button>
            </div>
        </div>
    )
}

export default myRecord_detail;