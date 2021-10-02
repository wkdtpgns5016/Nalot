import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import axios from 'axios'
import icon from '../Images/icon.png'

import {useHistory} from "react-router-dom"

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
        marginTop: theme.spacing(1),
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
    },

    submit: {
        margin: theme.spacing(3, 0, 2),
        backgroundColor: '#2ecc71',
        '&:hover': {
            backgroundColor: '#1f9451',
        }
    },
}));

function Signin() {
    const classes = useStyles();
    const history = useHistory();

    window.addEventListener('popstate', function(event) {
        history.push('/nalot')
    });

    const handleKeyPress = (e) =>{
        if(e.key === "Enter"){
            submitClicked()
        }
    }


    const submitClicked = () =>{
        let email = (document.getElementById('email').value)
        let password = (document.getElementById('password').value)
        //로컬머신 테스트로 localhost
        axios.post('http://localhost:8080/users/login',{
            "username": email, "password": password
        },{
            headers:{
                'Content-Type':'application/json'
            }
            })
            .then(response=>{
                axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.message}`
                axios.get('http://localhost:8080/users/'+email)
                    .then(res=>{
                        //console.log(res.data)
                        console.log(email)
                        history.push('/nalot/main',
                            {
                                "key" : 'Bearer ' + response.data.message,
                                "email" : email,
                                "name": res.data.name,
                                "id": email,
                                "gender": res.data.gender,
                                "zone_code": res.data.zoneCode,
                                "address_basic": res.data.addressBasic,
                                "address_detail": res.data.addressDetail,
                                "address_ground_number": res.data.addressGroundNumber,
                                "birth": res.data.birth,
                            }

                        )
                    })

            })
            .catch(err=>{
                alert(err.response.data.error)
            })
    }
    const signupclicked=()=>{
        history.push('/nalot/signup')
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                    <img style={{
                        width:'50px',
                        height:'50px'
                    }} src={icon} alt = "icon"/>
                <Typography component="h1" variant="h5">
                    Nalot
                </Typography>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="이메일 주소"
                        name="email"
                        autoComplete="email"
                        autoFocus
                        className={classes.root}
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="비밀번호"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                        onKeyPress={handleKeyPress}
                        className={classes.root}
                    />
                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        onClick = {submitClicked}
                        className={classes.submit}
                    >
                        로그인
                    </Button>
                    <Grid container>
                        <Grid item>
                            <a onClick={signupclicked} variant="body2">
                                {"회원가입"}
                            </a>
                        </Grid>
                    </Grid>
                </form>
            </div>

        </Container>

    );
}
export default Signin;