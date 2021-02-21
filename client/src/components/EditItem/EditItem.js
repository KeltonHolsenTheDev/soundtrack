import React from "react";
import "./EditItem";
import ItemForm from "../ItemForm";
import axios from "axios";

const EditItem = function ({ chosenItem, setEnableEdit, renderItems }) {
  const editItem = function (updatedItem) {
    // console.log(updatedItem);
    axios
      .put(`/api/item/${chosenItem.itemId}`, updatedItem)
      .then(function (response) {
        setEnableEdit(false);
        renderItems();
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  return (
    <ItemForm
      defaultItem={chosenItem}
      submitFcn={editItem}
      formTitle="Update"
    />
  );
};
export default EditItem;
