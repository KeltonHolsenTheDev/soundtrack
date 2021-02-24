import React from "react";
import axios from "axios";
import ItemForm from "../ItemForm";
import { useAuth } from "../../auth/auth";
import { useHistory } from "react-router-dom";

const AddItem = function () {
  useAuth();
  const history = useHistory();
  const addItem = function (item) {
    console.log(item);
    axios
      .post("/api/item", item)
      .then(function (response) {
        history.push("/");
      })
      .catch(function (error) {
        let errorMessage = "";
        for (let message of error.response.data) {
          errorMessage += message.defaultMessage + "\n";
        }
        alert(errorMessage);
        console.log(error.response.data);
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
    broken: false,
    notes: "",
  };
  return (
    <ItemForm defaultItem={blankItem} submitFcn={addItem} formTitle="Add" />
  );
};

export default AddItem;
