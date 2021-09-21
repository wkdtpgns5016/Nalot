import React from 'react'
import {Route} from "react-router-dom"
import Signin from './Signin'
import Signup  from "./Signup";
import Main from "./Main"
import myInformation from"./myInformation"
import recommendation from "./recommendation"
import recommendation2 from "./recommendation2";
import recommendation3 from "./recommendation3";
import recommendation_location from "./recommendation_location";
import myRecord from "./myRecord"
import myRecord_detail from "./myRecord_detail";
import myinformation_fix from "./myinformation_fix";

function Nalot({match}){
    return(
        <div>
            <Route exact path = {`${match.path}`} component = {Signin}/>
            <Route path = {`${match.path}/signup`} component={Signup}/>
            <Route path = {`${match.path}/main`} component={Main}/>
            <Route path = {`${match.path}/myinformation`} component = {myInformation}/>
            <Route path = {`${match.path}/recommendation`} compoenent = {recommendation}/>
            <Route path = {`${match.path}/recommendation2`} component = {recommendation2}/>
            <Route path = {`${match.path}/recommendation3`} component = {recommendation3}/>
            <Route path = {`${match.path}/recommendation_location`} component = {recommendation_location}/>
            <Route path = {`${match.path}/myRecord`} component = {myRecord}/>
            <Route path = {`${match.path}/myRecord_detail`} component = {myRecord_detail}/>
            <Route path = {`${match.path}/myinformation_fix`} component = {myinformation_fix}/>

        </div>
    )
}

export default Nalot;