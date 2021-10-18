import React, {useState} from 'react'
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import axios from'axios'

import {useHistory, useLocation} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {Divider, ListItem, ListItemText} from "@material-ui/core";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import Pagination from "../Components/Pagination";
import {paginate} from "../Components/paginate";
import Footer from "../Components/Footer";
import app from "../resources/application.json"

function myRecord() {
    let data;
    let year , month ,day
    const history = useHistory()
    const location = useLocation()
    console.log('myrecord'+ location.state.email)
    //console.log(location.state.data.length)
    const[logs, setlogs] = useState({
        log_data: location.state.data,
        pageSize : 3,
        currentPage: 1
    })

    const count = location.state.data.length

    const handlePageChange = (page) =>{
        setlogs({...logs, currentPage:page})
    }

    const {log_data,pageSize,currentPage} = logs;
    const pagedLogs = paginate(log_data, currentPage, pageSize)

    function clicked(e) {
        const id = e.currentTarget.id
        axios.get(app.ip+'/users/histories/'+location.state.email+'/'+e.currentTarget.id)
            .then(res=>{
                console.log(res.data)
                console.log(id)
                history.push('/nalot/myrecord_detail',
                    {
                        "key" : location.state.key,
                        "email" : location.state.email,
                        "detailed_data" : res.data,
                        "data" : location.state.data,
                        "value" : id
                    }
                )
            })
    }

    function renderData(){
        return pagedLogs.map(({userClothes, weather, id })=>{
            data = (userClothes.id).substring(0,8)
            year = data.substring(0,4)+"년"
            month = data.substring(4,6)+"월"
            day = data.substring(6,8)+"일"
            //return 안은 반복문
            return(
                <div style={
                    {
                        backgroundColor:'rgb(244,244,244)',
                        width:'600px',
                        marginBottom:'10px',
                        marginRight:'200px',
                        marginLeft:'35%'

                    }
                }>
                    <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                        <ListItem alignItems="flex-start">
                            <ListItemText
                                primary={year +" " +month +" " + day}
                                secondary={
                                    <React.Fragment>
                                        <Typography
                                            sx={{ display: 'inline' }}
                                            component="span"
                                            variant="body2"
                                            color="text.primary"
                                        >
                                            {userClothes.clothes.name}
                                            <h1 style={{
                                                color:'red',
                                                textAlign:'right'
                                            }}>{weather.temperature+"°C"}</h1>
                                        </Typography>

                                    </React.Fragment>

                                }
                            />
                            <Button
                                style={{maxWidth: '70px', maxHeight: '30px', minWidth: '70px', minHeight: '30px'}}
                                id={id}

                                onClick={e=>clicked(e,"id")}
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
                    />
                </div>

            )
        })
    }

    //renderdata전은 반복문 아님
    return(
        <div>
            <Header/>
            <Menu/>
                <h1>내기록</h1>
            {renderData()}
            <Pagination
                itemsCount={count}
                pageSize={logs.pageSize}
                currentPage={currentPage}
                onPageChange={handlePageChange}
                />
            <Footer/>
        </div>
    )
}

export default myRecord;