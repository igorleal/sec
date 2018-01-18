import React, { Component } from 'react';
import { Redirect } from 'react-router-dom'
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import Paper from 'material-ui/Paper';
import {makeLoginCall} from './../core/ApiCall';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      redirect: localStorage.getItem("token"),
      error: ""
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
    this.setState({redirect: token});
  }

  handleError(error) {
    console.log(error);
    this.setState({error: error.response.data.message || error.message});
  }

  handleSubmit() {
    const {username, password} = this.state;

    if (!username || !password) {
      this.setState({error: "Username and password are required"});
      return;
    }

    makeLoginCall(username, password)
    .then(response => this.handleSuccess(response))
    .catch(error => this.handleError(error));
  }

  render() {

    return (
        <div className="loginPage">
          { this.state.redirect && <Redirect to="/history"/> }
          <Paper className="myPaper" zDepth={1}>
          <p className="errorMessage">{this.state.error}</p>
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
