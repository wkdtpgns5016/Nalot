import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import axios from 'axios'
import DaumPostcode from 'react-daum-postcode'
import {FormControlLabel, Radio, RadioGroup} from "@material-ui/core";
import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header"
import Menu from "../Components/Menu"
import icon from "../Images/icon.png";
import Footer from "../Components/Footer";

const postCodeStyle = {
    display: "block",
    position: "absolute",
    top: "10%",
    width: "400px",
    height: "600px",
    padding: "7px",
    zIndex:"1",

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

function SignUp() {
    const classes = useStyles();
    const [open, setOpen] = useState(false)
    const [selectedValue, setSelectedValue] = React.useState(0);
    const [Password, setPassword] = useState("")
    const [confirmPassword, setconfirmPassword] = useState("")
    const [Gender, setGender] = useState("M")
    const location = useLocation()
    const history = useHistory()
    const [title, setTitle] = useState("우편번호찾기")

    const handleComplete = (data) => {
        let fullAddress = data.address;
        let extraAddress = '';

        if (data.bname !== '') {
            extraAddress += data.bname;
        }
        if (data.buildingName !== '') {
            extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
        }
        fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');

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
            setTitle("우편번호찾기")
        }
        else if (open === false) {
            setOpen(true)
            setTitle("우편번호찾기 창닫기")
        }
    }

    const buttonClick=()=>{
        if(Password === confirmPassword && typeof Password !== 'undefined'){
            let fname = (document.getElementById('fname').value)
            let date = (document.getElementById('date').value)
            let postcode = (document.getElementById('postcode').value)
            let address1 = (document.getElementById('address1').value)
            let address2 = (document.getElementById('address2').value)
            let address3 = (document.getElementById('address3').value)
            let email = (document.getElementById('email').value)
            let password = (document.getElementById('password').value)
            //let arr = [fname, lname, date, postcode, address1, address2, address3, email, password]
            let obj = {"name":fname, "birth": date, "zoneCode": postcode, "addressBasic": address1,
                "addressGroundNumber":address2, "addressDetail":address3, "id":email, "password":password, "gender":Gender}
            console.log(obj)
            //테스트 이기 때문에 localhost url 사용
            axios.put('http://54.180.117.194:8080/users/'+email,{
                "name":fname, "birth": date, "zoneCode": postcode, "addressBasic": address1,
                "addressGroundNumber":address2, "addressDetail":address3, "id":email,
                "password":password, "gender":Gender
            },{
                headers:{
                    'Content-Type':'application/json'
                }
            }).then(res=>{
                console.log(res.data.message)
                if(res.status===201){
                    alert(res.data.message)
                    history.push('/nalot/main',{
                        "key":location.state.key,
                        "email":location.state.email,
                        "id":location.state.email,
                    })
                }
            })
                .catch(error=>{
                    alert(error.response.data.errorMessage)
                })
        }
        else{
            return alert('비밀번호를 확인하세요')
        }
    }

    const prevClicked = () =>{
        history.push('/nalot/myinformation',{
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

    return (
        <div>
            <Header/>
            <Menu/>
            <Container component="main" maxWidth="sm">
                <CssBaseline />
                <div className={classes.paper}>
                    <img style={{
                        width:'50px',
                        height:'50px'
                    }} src={icon} alt = "icon"/>
                    <Typography component="h1" variant="h5">
                        정보 수정
                    </Typography>
                    <form className={classes.form} noValidate>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
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
                                    className={classes.root}
                                />
                            </Grid>
                            <RadioGroup>
                                <Grid item xs = {12} sm = {6}>
                                    <FormControlLabel

                                        value="M"
                                        control={<Radio color="primary"/>}
                                        label="남"
                                        labelPlacement="Start"
                                        onChange={handleRadioChange}
                                    />
                                </Grid>
                                <Grid item xs = {12} sm = {6}>
                                    <FormControlLabel
                                        value="F"
                                        control={<Radio color="primary"/>}
                                        label="여"
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
                                    label="생년월일"
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
                                    //label="우편번호"
                                    placeholder={location.state.zone_code}
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
                                    //label="기본주소"
                                    placeholder={location.state.address_basic}
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
                                    //label="지번주소"
                                    placeholder={location.state.address_ground_number}
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
                                    //label="상세주소"
                                    placeholder={location.state.address_detail}
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
                                    label="이메일 주소"
                                    value={location.state.id}
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
                                    label="비밀번호"
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
                                        hasNotSameError('password2') ? "입력한 비밀번호와 일치하지 않습니다" : null
                                    }
                                    variant="outlined"
                                    required
                                    fullWidth
                                    name="password2"
                                    label="비밀번호 확인"
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
                            수정하기
                        </Button>
                        <Button
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}
                            onClick={prevClicked}
                        >
                            뒤로가기
                        </Button>
                    </form>
                </div>
            </Container>
            <Footer/>
        </div>

    );
}
export default SignUp;