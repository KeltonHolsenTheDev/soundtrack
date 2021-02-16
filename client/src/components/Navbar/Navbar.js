import React from "react";
import { NavLink } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg">
      <NavLink className="navbar-brand" exact={true} to="/">
        🎧
      </NavLink>
      <button
        className="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>
      <div className="collapse navbar-collapse" id="navbarNav">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <NavLink
              exact={true}
              className="nav-link"
              activeClassName="nav-link active"
              to="/login"
            >
              Login
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink
              exact={true}
              className="nav-link"
              activeClassName="nav-link active"
              to="/"
            >
              Dashboard
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink
              exact={true}
              className="nav-link"
              activeClassName="nav-link active"
              to="/register"
            >
              Register
            </NavLink>
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default Navbar;
