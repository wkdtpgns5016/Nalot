import React, { useState} from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {Link} from "react-router-dom"
import axios from 'axios'
import DaumPostcode from 'react-daum-postcode'
import {FormControlLabel, Radio, RadioGroup} from "@material-ui/core";
import { useHistory } from "react-router-dom";
import icon from "../Images/icon.png";
import app from "../resources/application.json"

const postCodeStyle = {
    display: "block",
    position: "absolute",
    top: "10%",
    width: "600px",
    height: "400px",
    padding: "7px",
    zIndex:"1"
};
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
    submit: {
        margin: theme.spacing(3, 0, 2),
        backgroundColor: '#2ecc71',
        '&:hover': {
            backgroundColor: '#1f9451',
        }
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



}));

function SignUp() {
    const classes = useStyles();
    const [open, setOpen] = useState(false)
    const [Password, setPassword] = useState("")
    const [confirmPassword, setconfirmPassword] = useState("")
    const [Gender, setGender] = useState("")
    const [title, setTitle] = useState("??????????????????")

    const handleComplete = (data) => {
        let extraAddress = '';

        if (data.bname !== '') {
            extraAddress += data.bname;
        }
        if (data.buildingName !== '') {
            extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
        }

        document.getElementById('postcode').value = data.zonecode;
        document.getElementById('address1').value = data.address;
        document.getElementById('address2').value = data.jibunAddress;
        setOpen(false)


    }

    const onPasswordHandler = (event)=>{
        setPassword(event.currentTarget.value)
    }
    
    const onconfirmPasswordHandler = (event)=>{
        setconfirmPassword(event.currentTarget.value)
    }

    const hasNotSameError = passWordEntered =>
        Password!==confirmPassword ? true : false

    const handleRadioChange = (event) =>{
        setGender(event.currentTarget.value)
    }

    const handleClickOpen = () =>{
        if(open === true) {
            setOpen(false)
            setTitle("??????????????????")
        }
        else if (open === false) {
            setOpen(true)
            setTitle("?????????????????? ?????????")
        }
    }

    const buttonClick=()=>{
        if(Password === confirmPassword && typeof Password !== 'undefined'){
            let fname = (document.getElementById('fname').value)
            let lname = (document.getElementById('lname').value)
            let date = (document.getElementById('date').value)
            let postcode = (document.getElementById('postcode').value)
            let address1 = (document.getElementById('address1').value)
            let address2 = (document.getElementById('address2').value)
            let address3 = (document.getElementById('address3').value)
            let email = (document.getElementById('email').value)
            let password = (document.getElementById('password').value)
            //let arr = [fname, lname, date, postcode, address1, address2, address3, email, password]
            let obj = {"name":lname+fname, "birth": date, "zoneCode": postcode, "addressBasic": address1,
            "addressGroundNumber":address2, "addressDetail":address3, "id":email, "password":password, "gender":Gender}
            console.log(obj)
            //????????? ?????? ????????? localhost url ??????
            axios.post(app.ip+'/users', {
                "name":lname+fname, "birth": date, "zoneCode": postcode, "addressBasic": address1,
                "addressGroundNumber":address2, "addressDetail":address3, "id":email, "password":password, "gender":Gender

            },{
                headers:{
                 'Content-Type':'application/json'
                }
                })
                .then(response=>{
                    console.log(response.data.message)
                    if(response.status === 201) {
                        history.push('/Nalot');
                        alert(response.data.message)
                    }
                })
                .catch(error =>{
                    alert(error.response.data.errorMessage)
                })
         }
        else{
            return alert('??????????????? ???????????????')
        }


    }
    const history = useHistory();
    return (
        <Container component="main" maxWidth="md">
            <CssBaseline />
            <div className={classes.paper}>
                <img style={{
                    width:'50px',
                    height:'50px'
                }} src={icon} alt = "icon"/>
                <Typography component="h1" variant="h5">
                    ?????? ??????
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
                                label="??????"
                                autoFocus
                                inputProps={{maxLength:10}}
                                className={classes.root}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="lname"
                                label="???"
                                name="lname"
                                inputProps={{maxLength:3}}
                                className={classes.root}
                            />
                        </Grid>
                        <RadioGroup>
                            <Grid item xs = {12} sm = {6}>
                                <FormControlLabel
                                    value="M"
                                    control={<Radio color="primary"/>}
                                    label="???"
                                    labelPlacement="Start"
                                    onChange={handleRadioChange}
                                />
                            </Grid>
                            <Grid item xs = {12} sm = {6}>
                                <FormControlLabel
                                    value="F"
                                    control={<Radio color="primary"/>}
                                    label="???"
                                    labelPlacement="Start"
                                    onChange={handleRadioChange}
                                />
                            </Grid>
                        </RadioGroup>
                        <Grid item xs={12} sm ={6}>
                            <TextField
                                id="date"
                                required
                                variant={"outlined"}
                                label="????????????"
                                type="date"
                                defaultValue="2017-05-24"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                className={classes.root}
                            />
                        </Grid>
                        <Grid item xs={12} sm = {8}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="postcode"
                                placeholder="????????????"
                                name="postcode"
                                inputProps={{maxLength: 5}}
                                className={classes.root}
                            />
                        </Grid>
                        <Button item xs={12} sm = {4}
                            variant="outlined"
                            onClick={handleClickOpen}
                        >
                            {title}
                        </Button>

                        {
                            open ?
                                <DaumPostcode style={postCodeStyle} onComplete={handleComplete} autoClose
                                              open={open}
                                />
                                :null
                        }
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address1"
                                placeholder="????????????"
                                name="address1"
                                inputProps={{maxLength: 49}}
                                className={classes.root}
                            />
                        </Grid>
                        <Grid item xs = {12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address2"
                                placeholder="????????????"
                                name="address2"
                                inputProps={{maxLength: 49}}
                                className={classes.root}
                                />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address3"
                                label="????????????"
                                name="address3"
                                inputProps={{maxLength: 49}}
                                className={classes.root}
                            />
                        </Grid>
                        
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="????????? ??????"
                                name="email"
                                inputProps={{maxLength: 44}}
                                className={classes.root}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="password"
                                label="????????????"
                                type="password"
                                id="password"
                                onChange = {onPasswordHandler}
                                value = {Password}
                                inputProps={{maxLength: 49}}
                                className={classes.root}

                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                error = {hasNotSameError('password2')}
                                helperText={
                                    hasNotSameError('password2') ? "????????? ??????????????? ???????????? ????????????" : null
                                }
                                variant="outlined"
                                required
                                fullWidth
                                name="password2"
                                label="???????????? ??????"
                                type="password"
                                id="password2"
                                onChange = {onconfirmPasswordHandler}
                                value = {confirmPassword}
                                inputProps={{maxLength: 49}}
                                className={classes.root}
                            />
                        </Grid>
                    </Grid>
                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={buttonClick}
                    >
                        ????????????
                    </Button>
                    <Grid container justify="flex-end">
                        <Grid item>
                            <Link to = {`/Nalot`} variant="body2">
                                ?????? ????????? ???????????????? ???????????????
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </div>

        </Container>
    );
}
export default SignUp;