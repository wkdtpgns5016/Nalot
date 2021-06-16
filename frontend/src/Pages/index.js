import React from 'react'
import {Route} from "react-router-dom"
import Signin from './Signin'
import Signup  from "./Signup";
import Main from "./Main"

function Nalot({match}){
    return(
        <div>
            <Route exact path = {`${match.path}`} component = {Signin}/>
            <Route path = {`${match.path}/signup`} component={Signup}/>
            <Route path = {`${match.path}/main`} component={Main}/>
        </div>
    )
}

export default Nalot;