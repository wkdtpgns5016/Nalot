import React from 'react';
import Header from "../Components/Header"
import Menu from "../Components/Menu"
import axios from 'axios'


import {useLocation, useHistory} from "react-router-dom";
import {makeStyles} from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";
import Avatar from "@material-ui/core/Avatar";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
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

    return(
        <div>
        <Header/>
        <Menu/>
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    회원 정보
                </Typography>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                name="firstName"
                                variant="outlined"
                                required
                                fullWidth
                                id="fname"
                                label="이름"
                                value={location.state.name}
                                autoFocus
                                inputProps={{maxLength:10}}
                            />
                        </Grid>
                        <Grid item xs={12} sm ={6}>
                            <TextField
                                id="date"
                                required
                                variant={"outlined"}
                                label="생년월일"
                                type="date"
                                defaultValue="2017-05-24"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                name="firstName"
                                variant="outlined"
                                required
                                fullWidth
                                id="gender"
                                label="성별"
                                value={location.state.gender}
                                autoFocus
                                inputProps={{maxLength:10}}
                            />
                        </Grid>
                        <Grid item xs={12} sm = {8}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="postcode"
                                label="우편번호"
                                value={location.state.zone_code}
                                name="postcode"
                                inputProps={{maxLength: 5}}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address1"
                                label="기본주소"
                                value={location.state.address_basic}
                                name="address1"
                                inputProps={{maxLength: 49}}
                            />
                        </Grid>
                        <Grid item xs = {12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address2"
                                label="지번주소"
                                value={location.state.address_ground_number}
                                name="address2"
                                inputProps={{maxLength: 49}}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address3"
                                label="상세주소"
                                value={location.state.address_detail}
                                name="address3"
                                inputProps={{maxLength: 49}}
                            />
                        </Grid>

                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="이메일 주소"
                                value={location.state.id}
                                name="email"
                                inputProps={{maxLength: 44}}
                            />
                        </Grid>
                    </Grid>
                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={fixClicked}
                    >
                        정보수정
                    </Button>
                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={deleteClicked}
                    >
                        회원탈퇴
                    </Button>
                </form>
            </div>
        </Container>
        </div>
    )
}

export default myInformation