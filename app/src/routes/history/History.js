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
import {List, ListItem} from 'material-ui/List';
import FlatButton from 'material-ui/FlatButton';
import Paper from 'material-ui/Paper';

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
                <ListItem>
                    <Timestamp time={ it / 1000 } format='full' /> (<Timestamp time={ it / 1000 } />)<br />
                </ListItem>
                );
            })}
            <div className="btnFooter">
            <FlatButton secondary="true" label="Clear History" onClick={ this.handleClearHistory.bind(this) }/>
            </div>
        </div>
    ) : (<p className="emptyList">No login history</p>)

    if (this.state.redirect) {
      return (<Redirect to="/"/>);
    } else {
      return (
        <div className="historyPage">
        <Paper zDepth={5} className="myPaper">
          <List>
              {historyToRender}
          </List>
          </Paper>
        </div>
      );
    }
  }
}

export default History;
