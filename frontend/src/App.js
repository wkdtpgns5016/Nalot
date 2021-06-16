import React from 'react';
import './App.css';
import Nalot from './Pages'
import Signup from './Pages/Signup'
import Main from './Pages/Main'
import {Route, BrowserRouter as Router} from 'react-router-dom'
function App () {
  return (
      <div className="App">
          <Router>
            <Route exact path ="/nalot" component = {Nalot}/>
            <Route path = "/nalot/signup" component = {Signup}/>
              <Route path = "/nalot/main" component = {Main}/>
          </Router>
      </div>
  )
}
export default App;
