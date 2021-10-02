import React from 'react'
import Styles from "./Styles.css"
import head from'../Images/head.png'
import {Divider} from "@material-ui/core";

const Header = () =>{
    return(
        <div className="header">
            <div>
                <img style={{
                    padding:"10px",
                }}src={head} alt="head"/>
            </div>
            <Divider style={{
                margin:'20px'
            }}/>
        </div>
    )
}

export default Header;