import React from 'react'
import {Route} from "react-router-dom"
import Signin from './Signin'
import Signup  from "./Signup";
import Main from "./Main"
import myInformation from"./myInformation"
import recommendation from "./recommendation"

function Nalot({match}){
    return(
        <div>
            <Route exact path = {`${match.path}`} component = {Signin}/>
            <Route path = {`${match.path}/signup`} component={Signup}/>
            <Route path = {`${match.path}/main`} component={Main}/>
            <Route path = {`${match.path}/myinformation`} component = {myInformation}/>
            <Route path = {`${match.path}/recommendation`} compoenet = {recommendation}/>
        </div>
    )
}

export default Nalot;