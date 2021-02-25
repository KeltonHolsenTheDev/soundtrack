import React, { useContext } from "react";
import { AuthContext, useAuth } from "../../auth/auth";
import { NavLink, useHistory } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  useAuth();
  const { user, logoutUser } = useContext(AuthContext);
  const history = useHistory();

  const handleLogout = function (event) {
    event.preventDefault();
    logoutUser();
    history.push("/login");
  };

  return (
    <nav className="navbar navbar-dark navbar-expand-lg fixed-top">
      <NavLink className="navbar-brand" exact={true} to="/">
        🎧
      </NavLink>
      <button
        className="navbar-toggler "
        type="button"
        data-toggle="collapse"
        data-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon "></span>
      </button>
      <div className="collapse navbar-collapse" id="navbarNav">
        <ul className="navbar-nav ml-auto">
          {/* {user ? (
            ""
          ) : (
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
          )} */}

          {user ? (
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
          ) : (
            ""
          )}

          {user && user.access === "ROLE_ADMINISTRATOR" ? (
            <li className="nav-item">
              <NavLink
                exact={true}
                className="nav-link"
                activeClassName="nav-link active"
                to="/register"
              >
                Add User
              </NavLink>
            </li>
          ) : (
            ""
          )}

          {user ? (
            <li className="nav-location">
              <NavLink
                exact={true}
                className="nav-link"
                activeClassName="nav-link active"
                to="/location"
              >
                Add Location
              </NavLink>
            </li>
          ) : (
            ""
          )}

          {user && user.access === "ROLE_ADMINISTRATOR" ? (
            <li className="nav-item">
              <NavLink
                exact={true}
                className="nav-link"
                activeClassName="nav-link active"
                to="/event"
              >
                Create Event
              </NavLink>
            </li>
          ) : (
            ""
          )}

          {user && user.access === "ROLE_ADMINISTRATOR" ? (
            <li className="nav-item">
              <NavLink
                exact={true}
                className="nav-link"
                activeClassName="nav-link active"
                to="/item"
              >
                Add Item
              </NavLink>
            </li>
          ) : (
            ""
          )}

          {user ? (
            <li className="nav-item">
              <a
                className="nav-link"
                onClick={(e) => {
                  handleLogout(e);
                }}
                href="/"
              >
                Logout
              </a>
            </li>
          ) : (
            ""
          )}
        </ul>
      </div>
    </nav>
  );
}

export default Navbar;
