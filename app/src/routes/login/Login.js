import React, { Component } from 'react';
import './Login.css';
import axios from 'axios';
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from 'react-router-dom'
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import Divider from 'material-ui/Divider';
import Paper from 'material-ui/Paper';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      redirect: localStorage.getItem("token")
    }
  }

  handleChangeUsername(e) {
    this.setState({ username: e.target.value });
  }

  handleChangePassword(e) {
    this.setState({ password: e.target.value });
  }

  handleSuccess(response) {
    var token = response.data.token;
    localStorage.setItem("token", token);
    this.setState({redirect: true});
  }

  handleError(error) {
    alert("Error logging in");
    console.log(error);
  }

  handleSubmit() {
    const {username, password} = this.state;

    var instance = axios.create({
      baseURL: 'http://localhost:8080/',
    });

    instance.post('login', {
      username,
      password
    }).then(response => this.handleSuccess(response))
    .catch(error => this.handleError(error));
  }

  render() {

    return (
        <div className="loginPage">
          { this.state.redirect &&
            <Redirect to="/history"/>
          }
          <Paper className="myPaper" zDepth={2}>
          <TextField
            onChange={ this.handleChangeUsername.bind(this) } 
            value={this.state.username}
            hintText="Username"
            className="textInput"
          />
          <TextField
            onChange={ this.handleChangePassword.bind(this) } 
            value={this.state.password}
            hintText="Password"
            type="password"
            className="textInput"
          />
          <br />
          <div className="btnFooter">
            <RaisedButton label="Login" primary={true} onClick={ this.handleSubmit.bind(this) }/>
          </div>
          </Paper>
        </div>
      );
    }
}

export default Login;
