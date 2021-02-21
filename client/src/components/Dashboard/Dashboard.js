import React, { useState, useContext } from "react";
import { AuthContext } from "../../auth/auth";
import { NavLink, useHistory } from "react-router-dom";
import "./Dashboard.css";
import ItemContainer from "../ItemContainer";
import UserContainer from "../UserContainer";
import image from "../../img/IMG_2224.JPG";

const Dashboard = function () {
  const [showItems, setShowItems] = useState(false);
  const [showUsers, setShowUsers] = useState(false);
  const [enableEditItem, setEnableEditItem] = useState(false);
  const [enableEditUser, setEnableEditUser] = useState(false);
  const { user } = useContext(AuthContext);
  const history = useHistory();

  if (!user) {
    history.push("/login");
  }

  const handleShowItems = function (event) {
    setShowUsers(false);
    setShowItems(true);
    setEnableEditItem(false);
  };

  const handleShowUsers = function (event) {
    setShowItems(false);
    setShowUsers(true);
    setEnableEditUser(false);
  };

  return user ? (
    <div className="container-fluid dashboard-container ">
      <div className="row no-gutters dashboard-row">
        <div className="col-1 dashboard-col-2 px-0">
          <div className="card dashboard-card shadow rounded-0 w-100 h-100">
            <div className=" d-flex justify-content-center mt-2 mb-3">
              <img
                src={image}
                className="rounded-circle dashboard-img card-img-top border border-white "
                alt="user pic"
              />
            </div>
            {/* prettier-ignore */}
            <div className="dashboard-card-body">
              <h5 className="card-title text-center">
              {`${user.firstName} ${user.lastName}`}
                
              </h5>
              <h6 card-title> {user.accessLevel} </h6>
            </div>
            <ul className="list-group list-group-flush">
              <li className="list-group-item dashboard-card">
                <div className="card-body justify-content-center d-flex">
                  <a href="#" className="card-link">
                    <i class="fa fa-calendar fa-4x"></i>
                  </a>
                </div>
              </li>
              <li className="list-group-item dashboard-card">
                <div className="card-body justify-content-center d-flex">
                  <span className="card-link" onClick={handleShowItems}>
                    <i className="fa fa-shopping-basket fa-4x"></i>
                  </span>
                </div>
              </li>
              <li>
                <div className="card-body justify-content-center d-flex">
                  <span className="card-link" onClick={handleShowUsers}>
                    <i class="fa fa-address-book fa-4x"></i>
                  </span>
                </div>
              </li>
              <li>
                <div className="card-body justify-content-center d-flex">
                  <NavLink to="/location" className="card-link">
                    <i class="fa fa-map-signs fa-4x"></i>
                  </NavLink>
                </div>
              </li>
            </ul>
          </div>
        </div>
        {/* prettier-ignore */}
        <div className="col-11 dashboard-col p-0">
          {/* event card here */}
          {showUsers ? <UserContainer enableEdit={enableEditUser} setEnableEdit={setEnableEditUser} /> : ""}
          {showItems ? <ItemContainer enableEdit={enableEditItem} setEnableEdit={setEnableEditItem} /> : ""}
        </div>
      </div>
    </div>
  ) : (
    ""
  );
};
export default Dashboard;
