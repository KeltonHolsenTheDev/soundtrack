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
        JAK🎧
      </NavLink>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
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
            <li class="nav-item dropdown nav-dropdown-background">
              <a
                class="nav-link dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                Forms
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
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

                {user && user.access === "ROLE_ADMINISTRATOR" ? (
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
                      to="/item"
                    >
                      Add Item
                    </NavLink>
                  </li>
                ) : (
                  ""
                )}

                <div class="dropdown-divider"></div>
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
              </div>
            </li>
          ) : (
            ""
          )}

          <li class="nav-item">
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
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default Navbar;
