import React from 'react';
import './App.css';
import Nalot from './Pages'
import Signup from './Pages/Signup'
import Main from './Pages/Main'
import myInformation from './Pages/myInformation'
import recommendation from './Pages/recommendation'
import recommendation2 from './Pages/recommendation2'
import recommendation3 from "./Pages/recommendation3";
import recommendation_location from "./Pages/recommendation_location";
import myRecord from "./Pages/myRecord";
import myRecord_detail from "./Pages/myRecord_detail";
import myinformation_fix from "./Pages/myinformation_fix";
import {Route, BrowserRouter as Router} from 'react-router-dom'
function App () {
  return (
      <div className="App">
          <Router>
            <Route exact path ="/nalot" component = {Nalot}/>
            <Route path = "/nalot/signup" component = {Signup}/>
            <Route path = "/nalot/main" component = {Main}/>
            <Route path = "/nalot/myinformation" component = {myInformation}/>
            <Route path = "/nalot/recommendation" component = {recommendation}/>
            <Route path = "/nalot/recommendation2" component = {recommendation2}/>
            <Route path = "/nalot/recommendation3" component = {recommendation3}/>
            <Route path = "/nalot/recommendation_location" component = {recommendation_location}/>
            <Route path ="/nalot/myrecord" component = {myRecord}/>
            <Route path = "/nalot/myrecord_detail" component = {myRecord_detail}/>
            <Route path ="/nalot/myinformation_fix" component = {myinformation_fix}/>

          </Router>
      </div>
  )
}
export default App;
