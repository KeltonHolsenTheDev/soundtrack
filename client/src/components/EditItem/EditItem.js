import React, { useState } from "react";
import "./EditItem";
import ItemForm from "../ItemForm";
import axios from "axios";

const EditItem = function ({ chosenItem, setEnableEdit, renderItems }) {
  const [errors, setErrors] = useState([]);

  const editItem = function (updatedItem) {
    // console.log(updatedItem);
    axios
      .put(`/api/item/${chosenItem.itemId}`, updatedItem)
      .then(function (response) {
        setEnableEdit(false);
        renderItems();
      })
      .catch(function (error) {
        const newErrors = [];
        for (let message of error.response.data) {
          newErrors.push(message.defaultMessage);
        }
        setErrors(newErrors);
        // alert(error.response.data[0].defaultMessage);
      });
  };

  return (
    <ItemForm
      defaultItem={chosenItem}
      submitFcn={editItem}
      formTitle="Update"
      errors={errors}
      setErrors={setErrors}
    />
  );
};
export default EditItem;
