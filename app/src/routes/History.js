import React, { Component } from 'react';
import Timestamp from 'react-timestamp';
import { Redirect } from 'react-router-dom'
import {List, ListItem} from 'material-ui/List';
import FlatButton from 'material-ui/FlatButton';
import Paper from 'material-ui/Paper';
import {makeLoginHistoryListCall, makeLoginHistoryClearCall} from './../core/ApiCall';
import AppBar from 'material-ui/AppBar';

class History extends Component {
  constructor(props) {
    super(props);
    this.state = {
      history: [],
      message: "",
      logged: true
    }

    if (this.state.logged) {
      this.loadData();
    }
  }
  handleSuccess(response) {
      this.setState({ history: response.data })
      if (response.data.length === 0) {
        this.setState({message: "No login history"});
      }
  }

  handleError(error) {
    console.log(error);
    this.setState({message: error});
  }

  handleClearHistory() {
      if (this.state.history && this.state.history.length > 0) {
        makeLoginHistoryClearCall()
        .then(response => this.handleSuccess(response))
        .catch(error => this.handleError(error));
      }
  }

  loadData() {
    makeLoginHistoryListCall()
    .then(response => this.handleSuccess(response))
    .catch(error => this.handleError(error));
  }

  handleLogout() {
    localStorage.removeItem("token");
    this.setState({logged: false});
  }

  render() {

    const listOfLogins = this.state.history && this.state.history.map(function(it, index) {
        return ( 
        <ListItem>
            <p className="historyIndex"># {index + 1}</p> <Timestamp time={ it / 1000 } format='full' /> (<Timestamp time={ it / 1000 } />)<br />
        </ListItem>
        );
    });

    var historyListComponent =  (
        <div>
          {listOfLogins}
          <div className="btnFooter">
            <FlatButton secondary="true" label="Clear History" onClick={ this.handleClearHistory.bind(this) }/>
          </div>
      </div>
    );

    var message = <p className="emptyList">{this.state.message}</p>

    if (!this.state.logged) {
      return (<Redirect to="/"/>);
    } else {
      return (
        <div>
          <AppBar
            title={<span>My Security App</span>}
            iconElementLeft={<div />}
            iconElementRight={<FlatButton onClick={ this.handleLogout.bind(this)} label="Logout" />}
          />
          <div className="historyPage">
            <Paper zDepth={1} className="myPaper">
              <h2>Your 5 last successful logins</h2>
              <List>
                {this.state.message.length > 0 ? message : historyListComponent}
              </List>
            </Paper>
          </div>
        </div>
      );
    }
  }
}

export default History;
