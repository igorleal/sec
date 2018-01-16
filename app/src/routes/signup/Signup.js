import React, { Component } from 'react';
import './Signup.css';
import axios from 'axios';
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from 'react-router-dom'

class Signup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      firstName: "",
      lastName: "",
      redirect: localStorage.getItem("token")
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
    alert("Error signing up");
    console.log(error);
  }

  handleSubmit() {
    const {username, password, firstName, lastName} = this.state;

    var instance = axios.create({
      baseURL: 'http://localhost:8080/',
    });

    instance.post('signup', {
      username,
      password,
      firstName, 
      lastName
    }).then(response => this.handleSuccess(response))
    .catch(error => this.handleError(error));
  }

  render() {
    if (this.state.redirect) {
      return (<Redirect to="/history"/>);
    } else {
      return (
        <div>
          <input type="text" id="username" placeholder="username" 
            onChange={ this.handleChangeUsername.bind(this) } 
            value={this.state.username}
          />
          <br />
          <input type="password" id="password" placeholder="password" 
            onChange={ this.handleChangePassword.bind(this) } 
            value={this.state.password}
          />
          <br />
          <input type="text" id="firstname" placeholder="firstname" 
            onChange={ this.handleChangeFirstname.bind(this) } 
            value={this.state.firstName}
          />
          <br />
          <input type="text" id="lastname" placeholder="lastname" 
            onChange={ this.handleChangeLastname.bind(this) } 
            value={this.state.lastName}
          />
          <br /><br />
          <button
            onClick={ this.handleSubmit.bind(this) } 
          >Login</button>
        </div>
        
      );
    }
  }
}

export default Signup;
