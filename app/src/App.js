import React, { Component } from 'react';
import './App.css';
import History from './routes/History';
import {
  BrowserRouter as Router,
  Route
} from 'react-router-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Home from './routes/Home'


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      logged: localStorage.getItem("token")
    }
  }

  render() {
  
    return (
      <div>
        <MuiThemeProvider>
          <Router>
            <div>
              <Route exact path="/" component={Home}/>
              <Route path="/history" component={History}/>
            </div>
          </Router>
        </MuiThemeProvider>
      </div>
    );
  }
}

export default App;