import React, { useContext } from "react";
import { AuthContext } from "../../auth/auth";
import { useHistory } from "react-router-dom";
import "./Dashboard.css";

const Dashboard = function () {
  const { user } = useContext(AuthContext);
  const history = useHistory();

  if (!user) {
    history.push("/login");
  }
  return user ? (
    <div class="container-fluid dashboard-container ">
      <div class="row no-gutters">
        <div class="col-2 dashboard-col-2 px-0">
          <div class="card dashboard-card rounded-0 w-100">
            <div class=" d-flex justify-content-center mt-2">
              <img
                src="https://picsum.photos/70"
                class="rounded-circle dashboard-img card-img-top "
                alt="user pic"
              />
            </div>
            {/* prettier-ignore */}
            <div class="card-body dashboard-card-body">
              <h5 class="card-title text-center">
              {`${user.firstName} ${user.lastName}`}
                
              </h5>
              <h6 card-title> {user.accessLevel} </h6>
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item dashboard-card">
                <div class="card-body  text-center">
                  <a href="#" class="card-link">
                    Card link
                  </a>
                </div>
              </li>
              <li class="list-group-item dashboard-card">
                <div class=" card-body text-center">
                  <a href="#" class="card-link">
                    Card link
                  </a>
                </div>
              </li>
            </ul>
            <div class="card-body">
              <a href="#" class="card-link">
                Card link
              </a>
              <a href="#" class="card-link">
                Another link
              </a>
            </div>
          </div>
        </div>
        <div className="col p-0">{/* event card here */}</div>
      </div>
    </div>
  ) : (
    ""
  );
};
export default Dashboard;
