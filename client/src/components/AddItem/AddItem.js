import React from "react";
import axios from "axios";
import ItemForm from "../ItemForm";
import { useHistory } from "react-router-dom";

const AddItem = function () {
  const history = useHistory();
  const addItem = function (item) {
    axios
      .post("/api/item", item)
      .then(function (response) {
        history.push("/");
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  const blankItem = {
    itemId: 0,
    itemName: "",
    description: "",
    brand: "",
    itemType: "",
    itemCategory: "OTHER",
    location: { locationId: 0, name: "blank", address: "blank" },
    locationId: 0,
    locationDescription: "",
  };
  return (
    <ItemForm defaultItem={blankItem} submitFcn={addItem} formTitle="Add" />
  );
};

export default AddItem;
