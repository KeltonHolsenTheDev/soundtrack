import React, { useState } from "react";
import axios from "axios";

import "./Registration.css";

const Registration = function () {
  const blankUser = {
    id: 0,
    firstName: "",
    lastName: "",
    phone: "",
    email: "",
    accessLevel: "",
    password: "",
    roles: [],
  };

  const [user, setUser] = useState(blankUser);
  const [confirmPassword, setConfirmPassword] = useState("");

  const onChangeHandler = (event) => {
    const updatedUser = { ...user };
    updatedUser[event.target.name] = event.target.value;

    setUser(updatedUser);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (user.password == confirmPassword) {
      axios
        .post("/api/user", user)
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });
    } else {
      console.log("Passwords must match");
    }
  };

  return (
    <div className="container-fluid register-background">
      <div className="row ">
        <div className="col-3"></div>
        <div className="col-6 register-card-container d-flex mt-3">
          <div className="card shadow-lg px-4 w-100 rounded-0 register-card">
            <div className="card-body register-body">
              <h1 className="card-title mb-3 text-center">Register</h1>
              <form onSubmit={handleSubmit}>
                <div class="form-group register-form-group">
                  <label for="formGroupExampleInput">First Name</label>
                  <input
                    type="text"
                    name="firstName"
                    class="form-control"
                    id="formGroupExampleInput"
                    placeholder="Jane"
                    onChange={onChangeHandler}
                    value={user.firstName}
                  />
                </div>
                <div class="form-group register-form-group">
                  <label for="formGroupExampleInput2">Last Name</label>
                  <input
                    type="text"
                    name="lastName"
                    class="form-control"
                    id="formGroupExampleInput2"
                    placeholder="Doe"
                    onChange={onChangeHandler}
                    value={user.lastName}
                  />
                </div>
                <div class="form-group register-form-group">
                  <label for="formGroupExampleInput">Phone</label>
                  <input
                    type="tel"
                    name="phone"
                    class="form-control"
                    id="formGroupExampleInput"
                    placeholder="###-###-####"
                    onChange={onChangeHandler}
                    value={user.phone}
                  />
                </div>
                <div class="form-group register-form-group ">
                  <label for="inputEmail3">Email</label>
                  <div>
                    <input
                      type="email"
                      name="email"
                      class="form-control"
                      id="inputEmail3"
                      placeholder="jane@email.com"
                      onChange={onChangeHandler}
                      value={user.email}
                    />
                  </div>
                </div>
                <div class="form-group register-form-group">
                  <label for="inputPassword">Password</label>
                  <div>
                    <input
                      type="password"
                      name="password"
                      class="form-control"
                      id="inputPassword"
                      placeholder="Password"
                      onChange={onChangeHandler}
                      value={user.password}
                    />
                  </div>
                </div>
                <div class="form-group register-form-group">
                  <label for="inputConfirmPassword">Confirm Password</label>
                  <div>
                    <input
                      type="password"
                      name="confirmPassword"
                      class="form-control"
                      id="inputConfirmPassword"
                      placeholder="Confirm password"
                      onChange={(e) => {
                        setConfirmPassword(e.target.value);
                      }}
                      value={confirmPassword}
                    />
                  </div>
                </div>
                <label htmlFor="inputAccessLevel">Access Level</label>
                <select
                  name="accessLevel"
                  class="custom-select custom-select-sm mb-2"
                  value={user.accessLevel}
                  onChange={onChangeHandler}
                >
                  <option selected>Open this select menu</option>
                  <option value="ADMINISTRATOR">Administrator</option>
                  <option value="USER">User</option>
                </select>
                <div class="form-group register-form-group row">
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
