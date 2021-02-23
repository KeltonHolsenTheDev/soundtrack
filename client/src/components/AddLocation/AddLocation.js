import React from "react";
import axios from "axios";
import LocationForm from "../LocationForm";
import { useHistory } from "react-router-dom";

const AddLocation = function () {
  const history = useHistory();
  const addLocation = function (location) {
    console.log(location);
    axios
      .post("/api/location", location)
      .then(function (response) {
        history.push("/");
      })
      .catch(function (error) {
        alert(error.response.data);
      });
  };

  const blankLocation = {
    locationId: 0,
    name: "",
    address: ""
  };
  return (
    <LocationForm defaultLocation={blankLocation} submitFcn={addLocation} formTitle="Add" />
  );
};

export default AddLocation;
