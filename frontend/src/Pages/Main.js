import React from 'react';
import clsx from 'clsx';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import WbSunnyIcon from '@material-ui/icons/WbSunny';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import AccessibilityIcon from '@material-ui/icons/Accessibility';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import HomeIcon from '@material-ui/icons/Home';
import { useHistory, useLocation } from "react-router-dom";
import axios from'axios'

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
    },
    appBarShift: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    menuButton: {
        marginRight: 36,
    },
    hide: {
        display: 'none',
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
    },
    drawerOpen: {
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    drawerClose: {
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        overflowX: 'hidden',
        width: theme.spacing(7) + 1,
        [theme.breakpoints.up('sm')]: {
            width: theme.spacing(9) + 1,
        },
    },
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        // necessary for content to be below app bar
        ...theme.mixins.toolbar,
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
    },
}));

function Main() {
    const classes = useStyles();
    const theme = useTheme();
    const history = useHistory();
    const [open, setOpen] = React.useState(false);
    const location = useLocation();
    let nx, ny, loc;

    nx = "60"
    ny = "127"
    loc = "서울특별시"

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    const home = () =>{
        history.push('/nalot/main',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,
        })
    }

    const myInformation = () =>{

        history.push('/nalot/myinformation',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,

        })
    }

    const myRecord = () =>{
        console.log('myRecord')
    }
    const dateNow = () =>{
        let date = new Date();
        let year = date.getFullYear();
        let month = ("0" + (1 + date.getMonth())).slice(-2);
        let day = ("0" + date.getDate()).slice(-2);

        return year + month + day;
    }

    const hoursNow = () =>{
        let date = new Date();

        let hours = date.getHours();
        console.log((hours-1+'00'))
        //조건문 사용해서 02일 00시 -> 01시 23시로 바꿀것
        //매시 30분 마다 업뎃 -> getMinutes사용해서 30분ㅇ ㅣ후면 아워리턴 이전이면 아ㅜ어 -1 리턴
        return (hours-1) +'00';
    }

    const recommendation = () =>{
        //console.log('recommend')

        axios.post('http://localhost:8080/weathers/forecasts',{

            "date":dateNow(), "time":"0500","nx":nx , "ny": ny

        }, {
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`

            }
        })
            .then(response=>{
                console.log(response.data)
                console.log(response.data.temperatureCurrent)
                console.log(response.data.temperatureMax)
                console.log(response.data.temperatureMin)

                history.push('/nalot/recommendation',{
                    "key": location.state.key,
                    "name": location.state.name,
                    "id": location.state.id,
                    "gender": location.state.gender,
                    "zone_code": location.state.zone_code,
                    "address_basic": location.state.address_basic,
                    "address_detail": location.state.address_detail,
                    "address_ground_number": location.state.address_ground_number,
                    "birth": location.state.birth,

                    "current":response.data.temperatureCurrent,
                    "max":response.data.temperatureMax,
                    "min":response.data.temperatureMin,

                    "nx" : nx,
                    "ny" : ny,
                    "loc" : loc

                })
            })
    }

    const weatherNow = () =>{
        console.log('weatherNow')
    }

    const logOut = () =>{
        console.log('logOut')
    }

    const submitLocation = () =>{
        let s = document.getElementById("location")
        let v = s.options[s.selectedIndex].value
        console.log(v)
        if (v === "seoul"){
            nx = "60"
            ny = "127"
            loc = "서울특별시"
        }else if (v === "busan"){
            nx = "98"
            ny = "76"
            loc = "부산광역시"
        }else if (v === "daegu"){
            nx = "89"
            ny = "90"
            loc = "대구광역시"
        }else{
            nx = "60"
            ny = "127"
            loc = "서울특별시"
        }

        axios.post('http://localhost:8080/weathers/forecasts',{
            "date":dateNow(), "time":"0500","nx":nx , "ny": ny

        }, {
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`

            }
        }).then(response=>{
            history.push('/nalot/recommendation',{
                "key": location.state.key,
                "name": location.state.name,
                "id": location.state.id,
                "gender": location.state.gender,
                "zone_code": location.state.zone_code,
                "address_basic": location.state.address_basic,
                "address_detail": location.state.address_detail,
                "address_ground_number": location.state.address_ground_number,
                "birth": location.state.birth,

                "current":response.data.temperatureCurrent,
                "max":response.data.temperatureMax,
                "min":response.data.temperatureMin,

                "nx" : nx,
                "ny" : ny,
                "loc" : loc

            })
        })
    }

    const valueChanged = () =>{
        let s = document.getElementById("location")
        let v  = s.options[s.selectedIndex].value
    }

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar
                position="fixed"
                className={clsx(classes.appBar, {
                    [classes.appBarShift]: open,
                })}
            >
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        className={clsx(classes.menuButton, {
                            [classes.hide]: open,
                        })}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6">
                        Nalot&nbsp;&nbsp;&nbsp;
                    </Typography>
                    <Typography variant = "h6" color="inherit">{location.state.name}님</Typography>
                </Toolbar>
            </AppBar>
            <Drawer
                variant="permanent"
                className={clsx(classes.drawer, {
                    [classes.drawerOpen]: open,
                    [classes.drawerClose]: !open,
                })}
                classes={{
                    paper: clsx({
                        [classes.drawerOpen]: open,
                        [classes.drawerClose]: !open,
                    }),
                }}
            >
                <div className={classes.toolbar}>
                    <IconButton onClick={handleDrawerClose}>
                        {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
                    </IconButton>
                </div>
                <Divider />
                <List>
                    <ListItem button onClick={home}>
                        <ListItemIcon><HomeIcon/></ListItemIcon>
                        <ListItemText primary={'홈'}/>
                    </ListItem>
                        <ListItem button onClick={myInformation}>
                            <ListItemIcon><AssignmentIndIcon /></ListItemIcon>
                            <ListItemText primary={'내정보'} />
                        </ListItem>
                    <ListItem button onClick={myRecord}>
                        <ListItemIcon><InboxIcon /></ListItemIcon>
                        <ListItemText primary={'내 기록'} />
                    </ListItem>
                    <ListItem button onClick={recommendation}>
                        <ListItemIcon><AccessibilityIcon /></ListItemIcon>
                        <ListItemText primary={'의상 추천'} />
                    </ListItem>
                    <ListItem button onClick={weatherNow}>
                        <ListItemIcon><WbSunnyIcon /></ListItemIcon>
                        <ListItemText primary={'현재 날씨'} />
                    </ListItem>
                </List>
                <Divider />
                <List>
                    <ListItem button onClick={logOut}>
                        <ListItemIcon><ExitToAppIcon /></ListItemIcon>
                        <ListItemText primary={'로그아웃'} />
                    </ListItem>
                </List>
            </Drawer>
            <main className={classes.content}>
                <div className={classes.toolbar} />
                <div>
                    지역 설정
                </div>
                <div>
                    <select id="location" onChange={valueChanged}>
                        <option value="seoul" defaultValue={true}>서울특별시</option>
                        <option value="busan">부산광역시</option>
                        <option value="daegu">대구광역시</option>
                    </select>
                </div>
                <div>
                    <button
                        onClick={submitLocation}
                    >옷 추천받기</button>
                </div>
            </main>
        </div>
    );
}

export default Main;