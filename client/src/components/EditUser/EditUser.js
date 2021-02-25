import React, { useState } from "react";
import "./EditUser.css";
import UserForm from "../UserForm";
import axios from "axios";

const EditUser = function ({ chosenUser, setEnableEdit, renderUsers }) {
  const [errors, setErrors] = useState([]);

  const editUser = function (updatedUser, history) {
    axios
      .put(`/api/user/${chosenUser.userId}`, updatedUser)
      .then(function (response) {
        setEnableEdit(false);
        renderUsers();
      })
      .catch(function (error) {
        const newErrors = [];
        // for (let message of error.response.data) {
        //   newErrors.push(message.defaultMessage);
        // }
        console.log(error.response.data);
        setErrors(newErrors);
        // alert(error.response.data[0].defaultMessage);
      });
  };

  return (
    <UserForm
      defaultUser={chosenUser}
      submitFcn={editUser}
      formTitle="Update"
      errors={errors}
      setErrors={setErrors}
    />
  );
};
export default EditUser;
