import React from 'react';
import {Tabs, Tab} from 'material-ui/Tabs';
import FontIcon from 'material-ui/FontIcon';
import MapsPersonPin from 'material-ui/svg-icons/maps/person-pin';
import './Home.css';
import Login from './../login/Login'
import Signup from './../signup/Signup'

const Home = () => (
    <div className="homePage">
    <Tabs className="myTab">
        <Tab
        label="LOG IN"
        ><Login /></Tab>
        <Tab
        label="SIGN UP"
        ><Signup /></Tab>
    </Tabs>
</div>
);

export default Home;