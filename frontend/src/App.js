import React from 'react';
import './App.css';
import Nalot from './Pages'
import Signup from './Pages/Signup'
import {Route, BrowserRouter as Router} from 'react-router-dom'
function App () {
  return (
      <div className="App">
          <Router>
            <Route exact path ="/Nalot" component = {Nalot}/>
            <Route path = "/Nalot/Signup" component = {Signup}/>
          </Router>
      </div>
  )
}
export default App;
