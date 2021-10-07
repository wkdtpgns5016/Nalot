import React from 'react'
import {makeStyles} from "@material-ui/core/styles";
import {Avatar, IconButton} from "@material-ui/core";
import github from '../Images/github.png'

const useStyles = makeStyles((theme)=>({
    footer:{
        position:"fixed",
        bottom:"0",
        width:"100%",
        height:"auto",
        backgroundColor:"lightgrey",
        zIndex:"1"
    }
}))

const Footer = () =>{
    const classes = useStyles()
    return(
        <div className={classes.footer}>
            <div style={{

            }}>
                <IconButton
                    onClick={()=> window.open('https://github.com/wkdtpgns5016/Nalot', '_blank')}
                >
                    <Avatar src={github} alt = "github"/>
                </IconButton>
            </div>
            <div>
                Copyright © 장세훈, 홍정호
            </div>
        </div>
    )

}

export default Footer