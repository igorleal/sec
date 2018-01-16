import React, { Component } from 'react';
import './History.css';
import axios from 'axios';
import Timestamp from 'react-timestamp';
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from 'react-router-dom'

class History extends Component {
  constructor(props) {
    super(props);
    this.state = {
      history: [],
      redirect: !localStorage.getItem("token")
    }

    if (!this.state.redirect) {
      this.loadData();
    }
  }
  handleSuccess(response) {
      this.setState({ history: response.data })
    console.log(response.data);
  }

  handleError(error) {
    alert("Error retrieving history");
    console.log(error);
  }

  handleClearHistory() {
      if (this.state.history && this.state.history.length > 0) {
        var instance = axios.create({
            baseURL: 'http://localhost:8080/',
            headers: {'Authorization': 'Bearer' + localStorage.getItem('token')}
          });
      
          instance.delete('login/history').then(response => this.handleSuccess(response))
          .catch(error => this.handleError(error));
      }
  }

  loadData() {
    var instance = axios.create({
      baseURL: 'http://localhost:8080/',
      headers: {'Authorization': 'Bearer' + localStorage.getItem('token')}
    });

    instance.get('login/history').then(response => this.handleSuccess(response))
    .catch(error => this.handleError(error));
  }

  render() {

    var historyToRender = (this.state.history && this.state.history.length > 0) ? (
        <div>
            {this.state.history.map(function(it) {
                return ( 
                <div>
                    <Timestamp time={ it / 1000 } format='full' /> (<Timestamp time={ it / 1000 } />)<br />
                </div>
                );
            })}
            <button
            onClick={ this.handleClearHistory.bind(this) } 
            >Clear History</button>
        </div>
    ) : "No login history"

    if (this.state.redirect) {
      return (<Redirect to="/login"/>);
    } else {
      return (
        <div>
          <h1>History</h1>
          {historyToRender}
        </div>
        
      );
    }
  }
}

export default History;
