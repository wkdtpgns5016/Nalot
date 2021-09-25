import React from 'react';
import Header from "../Components/Header"
import Menu from "../Components/Menu"
import{useLocation} from "react-router-dom";

function Main() {
    let location=useLocation()
    console.log('main'+ location.state.email)
    return (
        <div>
            <Header/>
            <Menu/>
            main
        </div>
    );
}

export default Main;