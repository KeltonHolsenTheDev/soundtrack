import React from "react";

import "./Registration.css";

const Registration = function () {
  return (
    <div className="container-fluid register-background">
      <div className="row h-100">
        <div className="col-3"></div>
        <div className="col-6 register-card d-flex">
          <div className="card shadow-lg px-4 w-100 rounded-0">
            <div className="card-body">
              <h1 className="card-title mb-5 text-center">Register</h1>
              <form>
                <div class="form-group">
                  <label for="formGroupExampleInput">First Name</label>
                  <input
                    type="text"
                    class="form-control"
                    id="formGroupExampleInput"
                    placeholder="Jane"
                  />
                </div>
                <div class="form-group">
                  <label for="formGroupExampleInput2">Last Name</label>
                  <input
                    type="text"
                    class="form-control"
                    id="formGroupExampleInput2"
                    placeholder="Doe"
                  />
                </div>
                <div class="form-group">
                  <label for="formGroupExampleInput">Phone</label>
                  <input
                    type="tel"
                    class="form-control"
                    id="formGroupExampleInput"
                    placeholder="###-###-####"
                  />
                </div>
                <div class="form-group ">
                  <label for="inputEmail3">Email</label>
                  <div>
                    <input
                      type="email"
                      class="form-control"
                      id="inputEmail3"
                      placeholder="jane@email.com"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputPassword">Password</label>
                  <div>
                    <input
                      type="password"
                      class="form-control"
                      id="inputPassword"
                      placeholder="Password"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputConfirmPassword">Confirm Password</label>
                  <div>
                    <input
                      type="password"
                      class="form-control"
                      id="inputConfirmPassword"
                      placeholder="Confirm password"
                    />
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-10">
                    <button type="submit" class="btn btn-primary rounded-0">
                      Register
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div className="col-3"></div>
      </div>
    </div>
  );
};
export default Registration;
