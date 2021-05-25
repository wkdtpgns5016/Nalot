import React, {useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {Link} from "react-router-dom"

import DaumPostcode from 'react-daum-postcode'
import PropTypes from 'prop-types'
import Modal from "@material-ui/core/Modal";

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

}

const postCodeStyle = {
    display: "block",
    position: "absolute",
    top: "10%",
    width: "600px",
    height: "600px",
    padding: "7px",
};
const dialogStyle={
    width:'600px',
    height:'600px',
    backgroundColor: 'white'
};

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

function SimpleDialog(props) {
    const { onClose, selectedValue, open } = props;
    const handleClose = () => {
        onClose(selectedValue);
    };

    return (
        <Modal style={dialogStyle} onClose={handleClose} aria-labelledby="simple-dialog-title" open={open}>
            <DaumPostcode style={postCodeStyle} onComplete={handleComplete} />
        </Modal>
    );
}
SimpleDialog.propTypes = {
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired,
    selectedValue: PropTypes.string.isRequired,
};
function SignUp() {
    const classes = useStyles();
    const [open, setOpen] = useState(false)
    const [selectedValue, setSelectedValue] = React.useState(0);

    const handleClickOpen = () =>{
        setOpen(true)
    }
    const handleClose = (value) =>{
        setOpen(false)
        setSelectedValue(value);
    }
    const buttonClick=()=>{
        console.log(document.getElementById('fname').value)
        console.log(document.getElementById('lname').value)
        console.log(document.getElementById('date').value)
        console.log(document.getElementById('postcode').value)
        console.log(document.getElementById('address1').value)
        console.log(document.getElementById('address2').value)
        console.log(document.getElementById('address3').value)
        console.log(document.getElementById('email').value)
        console.log(document.getElementById('password').value)
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    회원 가입
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
                                autoFocus
                                inputProps={{maxLength:10}}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="lname"
                                label="성"
                                name="lname"
                                inputProps={{maxLength:3}}
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
                        <Grid item xs={12} sm = {8}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="postcode"
                                label="우편번호"
                                name="postcode"
                                autoComplete="postcode"
                                inputProps={{maxLength: 5}}
                            />
                        </Grid>
                        <Button item xs={12} sm = {4}
                            variant="outlined"
                            color="primary"
                            onClick={handleClickOpen}

                        >
                            우편번호찾기
                        </Button>
                        <SimpleDialog selectedValue={selectedValue} open={open} onClose={handleClose} />
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address1"
                                label="기본주소"
                                name="address1"
                            />
                        </Grid>
                        <Grid item xs = {12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address2"
                                label="지번주소"
                                name="address2"
                                />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="address3"
                                label="상세주소"
                                name="address3"
                            />
                        </Grid>
                        
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="이메일 주소"
                                name="email"
                                autoComplete="email"
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
                                autoComplete="current-password"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="password2"
                                label="비밀번호 확인"
                                type="password"
                                id="password2"
                                autoComplete="current-password"
                            />
                        </Grid>
                    </Grid>
                    <Button
                        //type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={buttonClick}
                    >
                        회원가입
                    </Button>
                    <Grid container justify="flex-end">
                        <Grid item>
                            <Link to = {`/Nalot`} variant="body2">
                                이미 계정이 있으신가요? 로그인하기
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </div>
        </Container>
    );
}
export default SignUp;