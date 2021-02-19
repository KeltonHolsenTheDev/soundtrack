import React from "react";
import "./EditUser.css";
import UserForm from "../UserForm";
import axios from "axios";

const EditUser = function ({ chosenUser }) {
  const editUser = function (updatedUser, history) {
    axios
      .put(`/api/user/${chosenUser.userId}`, updatedUser)
      .then(function (response) {
        history.push("/");
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  return (
    <UserForm
      defaultUser={chosenUser}
      submitFcn={editUser}
      formTitle="Update"
    />
  );
};
export default EditUser;
