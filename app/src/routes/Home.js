import React from 'react';
import {Tabs, Tab} from 'material-ui/Tabs';
import Login from './Login'
import Signup from './Signup'
import AppBar from 'material-ui/AppBar';

const Home = () => (
    <div>
        <AppBar
            title={<span>My Security App</span>}
            iconElementLeft={<div />}
            iconElementRight={<div />}
        />
        <div className="homePage">
            <Tabs className="myTab">
                <Tab label="LOG IN"><Login /></Tab>
                <Tab label="SIGN UP"><Signup /></Tab>
            </Tabs>
        </div>
    </div>
);

export default Home;