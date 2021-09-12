import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import axios from 'axios'

import {Link, useHistory} from "react-router-dom"

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
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

function Signin() {
    const classes = useStyles();
    const history = useHistory();
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
            .catch(error =>{
                console.log(error)
            })
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
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
                    />
                    <FormControlLabel
                        control={<Checkbox value="remember" color="primary" />}
                        label="Remember me"
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
                        <Grid item xs>
                            <Link href="#" variant="body2">
                                Forgot password?
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link to ={`nalot/signup`} variant="body2">
                                {"회원가입"}
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </div>

        </Container>
    );
}
export default Signin;