import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import setAuthToken from "../utils/setAuthToken";

// Create user context to get user in nested pages
export const AuthContext = React.createContext("auth");

export const logoutUser = (setUser, history) => {
  // Remove token from local storage
  localStorage.removeItem("jwtToken");
  // Remove auth header for future requests
  setAuthToken(false);
  // Set current user to empty object {} which will set isAuthenticated to false
  setUser(null);
  history.push("/login");
};

export const loginUser = (setUser, setErrors) => (userData, history) => {
  // export const loginUser = (setUser) => (userData, history) => {
  // console.log(userData);
  axios
    .post("/login", userData)
    .then((res) => {
      // Save to localStorage

      // Set token to localStorage
      const { jwt_token } = res.data;
      localStorage.setItem("jwtToken", jwt_token);
      // Set token to Auth header
      setAuthToken(jwt_token);
      // Decode token to get user data
      const decoded = jwt_decode(jwt_token);
      // Set current user
      setUser(decoded);
      console.log(decoded);
      history.push("/");
    })
    .catch((err) => {
      console.log(err);
      // setErrors(err.response.data);
    });
};

export const registerUser = (setErrors) => (userData, history) => {
  // export const registerUser = () => (userData, history) => {
  // console.log(userData);
  axios
    .post("api/user", userData)
    .then((res) => history.push("/login"))
    .catch((err) => {
      console.log(err.response.data);
      // setErrors(err.response.data);
    });
};

export function useAuth() {
  const [user, setUser] = useState(null);
  const [errors, setErrors] = useState({});
  useEffect(() => {
    if (localStorage.jwtToken) {
      // Set auth token header auth
      const token = localStorage.jwtToken;
      setAuthToken(token);
      // Decode token and get user info and exp
      const decoded = jwt_decode(token);
      // Set user and isAuthenticated
      setUser(decoded);
      // Check for expired token
      const currentTime = Date.now() / 1000; // to get in milliseconds
      if (decoded.exp < currentTime) {
        // Logout user
        logoutUser(setUser);

        // Redirect to login
        window.location.href = "./login";
      }
    }
  }, []);

  return {
    user,
    errors,
    loginUser: loginUser(setUser, setErrors),
    // loginUser: loginUser(setUser),
    logoutUser: (history) => logoutUser(setUser, history),
    registerUser: registerUser(setErrors),
    // registerUser: registerUser(),
  };
}

export function Auth({ children }) {
  const { user, errors, loginUser, logoutUser, registerUser } = useAuth();
  // const { user, loginUser, logoutUser, registerUser } = useAuth();
  return (
    <AuthContext.Provider
      value={{ user, errors, loginUser, logoutUser, registerUser }}
      // value={{ user, loginUser, logoutUser, registerUser }}
    >
      {children}
    </AuthContext.Provider>
  );
}
