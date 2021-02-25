import React, { useState, useContext } from "react";
import { AuthContext, useAuth } from "../../auth/auth";
import { useHistory } from "react-router-dom";
import UserForm from "../UserForm";

import "./Registration.css";

const Registration = function () {
  useAuth();
  const { user, registerUser, errors, setErrors } = useContext(AuthContext);
  const history = useHistory();

  if (!user || user.access !== "ROLE_ADMINISTRATOR") {
    history.push("/login");
  }

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

  const addUser = function (newUser, history) {
    registerUser(newUser, history);
  };

  return (
    <UserForm
      defaultUser={blankUser}
      submitFcn={addUser}
      formTitle="Register"
      errors={errors}
      setErrors={setErrors}
    />
  );
};
export default Registration;
