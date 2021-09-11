import React from 'react'
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
import { useHistory, useLocation } from "react-router-dom";
import HomeIcon from "@material-ui/icons/Home";

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


function recommendation2(){
    const classes = useStyles();
    const theme = useTheme();
    const history = useHistory();
    const [open, setOpen] = React.useState(false);
    const location = useLocation();

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

    const recommendation = () =>{
        console.log('recommend')

        history.push('/nalot/recommendation_location',{
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

    const weatherNow = () =>{
        console.log('weatherNow')
    }

    const logOut = () =>{
        console.log('logOut')
    }

    const prevClicked = () =>{
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

            "current":location.state.current,
        })
    }

    const nextClicked = () =>{
        history.push('/nalot/recommendation3',{
            "key": location.state.key,
            "name": location.state.name,
            "id": location.state.id,
            "gender": location.state.gender,
            "zone_code": location.state.zone_code,
            "address_basic": location.state.address_basic,
            "address_detail": location.state.address_detail,
            "address_ground_number": location.state.address_ground_number,
            "birth": location.state.birth,

            "current":location.state.current,

        })
    }
    return(

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
                    <span>선택한 의상</span>
                    &nbsp;
                    &nbsp;
                    &nbsp;
                    <span>톤온톤 매치</span>
                </div>
                <br></br>
                <div>
                    <button>선택한 의상</button>
                    &nbsp;
                    &nbsp;
                    &nbsp;
                    <button>톤온톤 매치</button>
                </div>
                <br></br>
                <div>
                    <span>선택한 색상</span>
                    &nbsp;
                    &nbsp;
                    &nbsp;
                    <span>톤인톤 매치</span>
                </div>
                <div>
                    <button>선택한 색상</button>
                    &nbsp;
                    &nbsp;
                    &nbsp;
                    <button>톤인톤 매치</button>
                </div>
                <div>
                <button
                    onClick={prevClicked}
                >이전</button>
                <button
                onClick={nextClicked}
                >다음</button>
                </div>
            </main>

        </div>


    );
}

export default recommendation2;