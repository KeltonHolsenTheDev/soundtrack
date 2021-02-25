import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import setAuthToken from "../utils/setAuthToken";

// Create user context to get user in nested pages
export const AuthContext = React.createContext("auth");

export const logoutUser = (setUser) => {
  // Remove token from local storage
  localStorage.removeItem("jwtToken");
  // Remove auth header for future requests
  setAuthToken(false);
  // Set current user to empty object {} which will set isAuthenticated to false
  setUser(null);
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
      history.push("/");
    })
    .catch((err) => {
      // alert("email or password incorrect");
      // console.log(err.response);
      const newErrors = [];
      newErrors.push("email or password incorrect");
      setErrors(newErrors);
    });
};

export const registerUser = (setErrors) => (userData, history) => {
  // export const registerUser = () => (userData, history) => {
  // console.log(userData);
  axios
    .post("api/user", userData)
    .then((res) => history.push("/"))
    .catch((error) => {
      const newErrors = [];
      for (let message of error.response.data) {
        newErrors.push(message.defaultMessage);
      }
      setErrors(newErrors);
      // alert(error.response.data[0].defaultMessage);
      // alert(error.response.data);
      // console.log(error);
      // setErrors(err.response.data);
    });
};

export function useAuth() {
  const [user, setUser] = useState(null);
  const [errors, setErrors] = useState([]);
  // const [expiredAlert, setExpiredAlert] = useState(false);
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
        alert("Your session has expired.");
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
    setErrors,
    loginUser: loginUser(setUser, setErrors),
    // loginUser: loginUser(setUser),
    logoutUser: () => logoutUser(setUser),
    registerUser: registerUser(setErrors),
    // registerUser: registerUser(),
  };
}

export function Auth({ children }) {
  const {
    user,
    errors,
    setErrors,
    loginUser,
    logoutUser,
    registerUser,
  } = useAuth();
  // const { user, loginUser, logoutUser, registerUser } = useAuth();
  return (
    <AuthContext.Provider
      value={{
        user,
        errors,
        setErrors,
        loginUser,
        logoutUser,
        registerUser,
      }}
      // value={{ user, loginUser, logoutUser, registerUser }}
    >
      {children}
    </AuthContext.Provider>
  );
}
