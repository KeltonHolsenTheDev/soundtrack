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
    <div className="container-fluid dashboard-container ">
      <div className="row no-gutters">
        <div className="col-2 dashboard-col-2 px-0">
          <div className="card dashboard-card shadow rounded-0 w-100">
            <div className=" d-flex justify-content-center mt-2">
              <img
                src="https://picsum.photos/70"
                className="rounded-circle dashboard-img card-img-top border border-white "
                alt="user pic"
              />
            </div>
            {/* prettier-ignore */}
            <div className="card-body dashboard-card-body">
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
                  <a href="#" className="card-link">
                    <i class="fa fa-comments fa-4x"></i>
                  </a>
                </div>
              </li>
            </ul>
            <div className="card-body justify-content-center d-flex">
              <a href="#" className="card-link">
                <i class="fa fa-address-book fa-4x"></i>
              </a>
            </div>
          </div>
        </div>
        <div className="col dashboard-col p-0">{/* event card here */}</div>
      </div>
    </div>
  ) : (
    ""
  );
};
export default Dashboard;
