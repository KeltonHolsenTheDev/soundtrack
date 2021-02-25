import React, { useState, useContext } from "react";
import { AuthContext } from "./auth/auth";
import { Auth } from "./auth/auth";
import "./App.css";
import { useImmerReducer } from "use-immer";
import Login from "./components/Login";
import Registration from "./components/Registration";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Navbar from "./components/Navbar";
import Dashboard from "./components/Dashboard";
import AddLocation from "./components/AddLocation";
import AddItem from "./components/AddItem";
import AddEvent from "./components/AddEvent";
import Modal from "./components/Modal";
import ReactCardFlip from "react-card-flip";

const App = () => {
  const { user, setUser } = useContext(AuthContext);

  return (
    <Router>
      <Auth>
        <Navbar />
        <Switch>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/event">
            <AddEvent />
          </Route>
          <Route path="/location">
            <AddLocation />
          </Route>
          <Route path="/register">
            <Registration />
          </Route>
          <Route path="/item">
            <AddItem />
          </Route>
          <Route path="/">
            <Dashboard />
          </Route>
        </Switch>
      </Auth>
    </Router>
  );
};

export default App;
