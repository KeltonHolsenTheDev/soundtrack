import React, { useState, useContext } from "react";
import { AuthContext } from "../../auth/auth";
import "./Login.css";
import { useHistory } from "react-router-dom";

const Login = function () {
  const { loginUser, errors } = useContext(AuthContext);

  const history = useHistory();
  const blankUser = {
    email: "",
    password: "",
  };

  const [login, setLogin] = useState(blankUser);

  const onChangeHandler = (event) => {
    const updatedUser = { ...login };
    updatedUser[event.target.name] = event.target.value;

    setLogin(updatedUser);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    loginUser(login, history);
  };

  return (
    <div className="container-fluid login-container">
      <div className="row">
        <div className="d-none d-xl-block col-1 home-brand-container">
          {/* <h1 className="text-light">SoundTrack</h1> */}
        </div>
        <div className="d-none col-5 home-background p-0 d-xl-flex justify-content-end">
          <div className="home-headphones w-100"></div>
        </div>
        <div className="col-12 col-xl-6 login-card-container d-flex">
          <div className="card login-card shadow-lg rounded-0">
            <div className="card-body">
              <h1 className="card-title mt-3 text-center mb-3">Sign In</h1>
              <p className="card-text mb-5 text-center login-text text-muted">
                Be doers of the word, and not hearers only
              </p>
              {errors.map((error) => (
                <p
                  key={errors.indexOf(error)}
                  className="card-text error-item-form"
                >
                  {error}
                </p>
              ))}
              <form onSubmit={handleSubmit}>
                <div className="form-group login-form-group row">
                  <label
                    htmlFor="staticEmail"
                    className="col-3 col-form-label text-right"
                  >
                    Email
                  </label>
                  <div className="col-8">
                    <input
                      type="email"
                      name="email"
                      className="form-control"
                      id="staticEmail"
                      value={login.email}
                      onChange={onChangeHandler}
                      placeholder="email@email.com"
                      required
                    />
                  </div>
                  <div className="col-1"></div>
                </div>
                <div className="form-group login-form-group row">
                  <label
                    htmlFor="inputPassword"
                    className="col-3 col-form-label text-right"
                  >
                    Password
                  </label>
                  <div className="col-8">
                    <input
                      type="password"
                      name="password"
                      className="form-control"
                      id="inputPassword"
                      placeholder="Password"
                      value={login.password}
                      onChange={onChangeHandler}
                      required
                    />
                  </div>
                  <div className="col-1"></div>
                </div>
                <div className="row login-button-container">
                  <div className="col-2"></div>
                  <div className="col-10">
                    <button type="submit" className="btn btn-primary rounded-0">
                      Login
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Login;
