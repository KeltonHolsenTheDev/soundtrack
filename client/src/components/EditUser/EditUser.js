import React from "react";
import "./EditUser.css";
import UserForm from "../UserForm";
import axios from "axios";

const EditUser = function ({ chosenUser, setEnableEdit, renderUsers }) {
  const editUser = function (updatedUser, history) {
    axios
      .put(`/api/user/${chosenUser.userId}`, updatedUser)
      .then(function (response) {
        setEnableEdit(false);
        renderUsers();
      })
      .catch(function (error) {
        console.log(error.response.data);
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
