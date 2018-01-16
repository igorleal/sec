import React, { Component } from 'react';
import './App.css';
import Login from './routes/login/Login';
import History from './routes/history/History';
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom'

class App extends Component {
  
  render() {
    return (
      <div className="App">
        <h1>App</h1>
        <Router>
          <div>
            <Link to="/login">Login</Link>
            <Link to="/history">History</Link>

            <Route exact path="/" component={Home}/>
            <Route path="/login" component={Login}/>
            <Route path="/history" component={History}/>
          </div>
        </Router>
      </div>
    );
  }
}

export default App;

const Home = () => (
  <div>
  </div>
)