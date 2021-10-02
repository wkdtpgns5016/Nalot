import React from 'react';
import Header from "../Components/Header"
import Menu from "../Components/Menu"
import axios from 'axios'


import {useLocation, useHistory} from "react-router-dom";
import {makeStyles} from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import icon from "../Images/icon.png";
import Footer from "../Components/Footer";

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    root: {
        '& label.Mui-focused': {
            color: '#2ecc71',
        },
        '& .MuiInput-underline:after': {
            borderBottomColor: '#2ecc71',
        },
        '& .MuiOutlinedInput-root': {
            '& fieldset': {

            },
            '&:hover fieldset': {
                borderColor: '#2ecc71',
            },
            '&.Mui-focused fieldset': {
                borderColor: '#2ecc71',
            },
        },
        marginRight:theme.spacing(8),
        marginTop: theme.spacing(4),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
        backgroundColor: '#2ecc71',
        '&:hover': {
            backgroundColor: '#1f9451',
        }
    },
}));

function myInformation(){
    const classes = useStyles()
    const location = useLocation()
    const history = useHistory()

    const fixClicked=()=>{
        history.push('/nalot/myinformation_fix',{
            "key" : location.state.key,
            "name" : location.state.name,
            "id" : location.state.email,
            "gender" : location.state.gender,
            "zone_code" : location.state.zone_code,
            "address_basic" : location.state.address_basic,
            "address_detail" : location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth" : location.state.birth,
            "email" : location.state.email,
        })
    }
    const deleteClicked=()=>{
        axios.delete('http://localhost:8080/users/'+location.state.email)
            .then(res=>{
                if(res.status===201){
                    alert(res.data.message)
                    history.push('/nalot')
                }
                }
            ).catch(error=>{
                alert(error.response.data.errorMessage)
        })
    }
    console.log('myinfo'+ location.state.email)
    console.log(location.state)

    return(
        <div>
        <Header/>
        <Menu/>

        <Container component="main" maxWidth="lg">
            <Grid
                container
                spacing={3}
                direction="row"
                justify="center"
                alignItems="stretch"
            >
                <Grid item xs={4}
                >
                    <Grid style={{height:"100%"}}>
                        <div>
                            <h1>내 정보</h1>
                        </div>
                        <img src={icon} alt ='logo'/>
                    </Grid>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        name="firstName"
                        variant="outlined"
                        id="email"
                        label="이메일"
                        value={location.state.email}
                        className={classes.root}
                        style = {{width: 300}}
                    />
                    <TextField
                        name="firstName"
                        variant="outlined"
                        id="postcode"
                        label="우편번호"
                        value={location.state.zone_code}
                        className={classes.root}
                        style = {{width: 120}}
                    />
                    <TextField
                        name="firstName"
                        variant="outlined"
                        id="fname"
                        label="이름"
                        value={location.state.name}
                        className={classes.root}
                        style = {{width: 120}}
                    />
                    <TextField
                        name="firstName"
                        variant="outlined"
                        id="address1"
                        label="기본주소"
                        value={location.state.address_basic}
                        className={classes.root}
                        style = {{width: 300}}
                    />
                    <TextField
                        name="firstName"
                        variant="outlined"
                        id="gender"
                        label="성별"
                        value={location.state.gender}
                        className={classes.root}
                        style = {{width: 120}}
                    />
                    <TextField
                        variant="outlined"
                        id="address2"
                        label="지번주소"
                        value={location.state.address_ground_number}
                        name="address2"
                        className={classes.root}
                        style = {{width: 300}}
                    />
                    <TextField
                        id="date"
                        variant={"outlined"}
                        label="생년월일"
                        value={location.state.birth.slice(0,10)}
                        className={classes.root}
                        style = {{width: 120}}
                    />
                    <TextField
                        variant="outlined"
                        id="address3"
                        label="상세주소"
                        value={location.state.address_detail}
                        name="address3"
                        className={classes.root}
                        style = {{width: 300}}
                    />
                </Grid>

            </Grid>
        </Container>
            <Button
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={fixClicked}
            >
                정보수정
            </Button>
            <Button
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={deleteClicked}
            >
                회원탈퇴
            </Button>
            <Footer/>
        </div>
    )
}

export default myInformation