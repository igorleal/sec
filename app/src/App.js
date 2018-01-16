import React, { Component } from 'react';
import './App.css';
import Login from './routes/login/Login';
import Signup from './routes/signup/Signup';
import History from './routes/history/History';
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import FlatButton from 'material-ui/FlatButton';
import IconButton from 'material-ui/IconButton';


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      logged: localStorage.getItem("token")
    }
  }

  handleLogout() {
    localStorage.removeItem("token");
  }

  static muiName = 'FlatButton';

  render() {
  
    return (
      <div>
          <MuiThemeProvider>
          <AppBar
              title={<span>My Security App</span>}
              iconElementLeft={<div />}
              iconElementRight={this.state.logged ? <FlatButton label="Logout" /> : ""}
            />


            <Router>
              <div>
                <Route exact path="/" component={Login}/>
                <Route path="/login" component={Login}/>
                <Route path="/history" component={History}/>
                <Route path="/signup" component={Signup}/>
              </div>
            </Router>


              </MuiThemeProvider>
        </div>
      
    );
  }
}

export default App;