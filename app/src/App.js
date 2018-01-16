import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      result: ""
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
  }

  handleError(error) {
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
      <div className="App">
        {this.state.error}
        <input type="text" id="username" placeholder="username" 
          onChange={ this.handleChangeUsername.bind(this) } 
          value={this.state.username}
        />
        <br />
        <input type="password" id="password" placeholder="password" 
          onChange={ this.handleChangePassword.bind(this) } 
          value={this.state.password}
        />
        <br /><br />
        <button
          onClick={ this.handleSubmit.bind(this) } 
        >Login</button>
      </div>
      
    );
  }
}

export default App;
