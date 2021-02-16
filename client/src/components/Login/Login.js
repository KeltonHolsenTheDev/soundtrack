import React from "react";
import "./Login.css";

const Login = function () {
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="d-none d-xl-block col-1 home-brand-container">
          <h1 className="text-light">JAK</h1>
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
              <form>
                <div className="form-group row">
                  <label
                    htmlFor="staticEmail"
                    className="col-sm-2 col-form-label text-right"
                  >
                    Email
                  </label>
                  <div className="col-sm-9">
                    <input
                      type="text"
                      className="form-control"
                      id="staticEmail"
                      value="email@example.com"
                    />
                  </div>
                  <div className="col-1"></div>
                </div>
                <div className="form-group row">
                  <label
                    htmlFor="inputPassword"
                    className="col-sm-2 col-form-label text-right"
                  >
                    Password
                  </label>
                  <div className="col-sm-9">
                    <input
                      type="password"
                      className="form-control"
                      id="inputPassword"
                      placeholder="Password"
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
            <div className="card-footer text-center">
              Don't have an account? <a href="/register">Sign up</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Login;
