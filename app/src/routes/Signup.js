import React, { Component } from 'react';
import { Redirect } from 'react-router-dom'
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import Paper from 'material-ui/Paper';
import {makeSignupCall} from './../core/ApiCall';

class Signup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      firstName: "",
      lastName: "",
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

  handleChangeFirstname(e) {
    this.setState({ firstName: e.target.value });
  }

  handleChangeLastname(e) {
    this.setState({ lastName: e.target.value });
  }

  handleSuccess(response) {
    var token = response.data.token;
    localStorage.setItem("token", token);
    this.setState({redirect: true});
  }

  handleError(error) {
    console.log(error);
    this.setState({error: error.response.data.error || error.message});
  }

  handleSubmit() {
    const {username, password, firstName, lastName} = this.state;

    if (!username || !password || !firstName || !lastName) {
      this.setState({error: "All fields are required"});
      return;
    }

    makeSignupCall(firstName, lastName, username, password)
    .then(response => this.handleSuccess(response))
    .catch(error => this.handleError(error));
  }

  render() {
      return (
        <div className="signupPage">
          { this.state.redirect &&
            <Redirect to="/history"/>
          }
          <Paper className="myPaper" zDepth={1}>
          <p className="errorMessage">{this.state.error}</p>
          <TextField
            onChange={ this.handleChangeFirstname.bind(this) } 
            value={this.state.firstName}
            hintText="First Name"
            className="textInput"
          />
          <TextField
            onChange={ this.handleChangeLastname.bind(this) } 
            value={this.state.lastName}
            hintText="Last Name"
            className="textInput"
          />
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
            <RaisedButton label="Signup" primary={true} onClick={ this.handleSubmit.bind(this) }/>
          </div>
          </Paper>
        </div>
      );
  }
}

export default Signup;
