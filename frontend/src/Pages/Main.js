import React from 'react';
import Header from "../Components/Header"
import Menu from "../Components/Menu"
import{useLocation} from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import {Box, Container} from "@material-ui/core";
import logo from '../Images/logo.png'

function Main() {
    let location=useLocation()
    console.log('main'+ location.state.email)
    return (
        <div>
            <Header/>
            <Menu/>
            <p/>
            <Container component="main" maxWidth="lg" style={{

            }}>
                <Box sx={{ flexGrow: 1 }}>
                    <Grid container spacing={2}>
                        <Grid item xs={4}>
                            <img src={logo} alt ='logo'/>
                        </Grid>
                        <Grid item xs={8}>
                            <h2>Nalot</h2>이란..?
                            <p>
                                Nalot은 날씨 별 의상 추천 웹서비스로서...
                            </p>
                            <p>
                                의상 추천 메뉴에서 날씨에 따른 의상 추천을 받아보고,
                            </p>
                            <p>
                                의상 색상과 매칭 색상을 정해보세요!
                            </p>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
        </div>
    );
}

export default Main;