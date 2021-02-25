import React, { useState, useContext } from "react";
import { useAuth } from "../../auth/auth";
import axios from "axios";
import LocationForm from "../LocationForm";
import { useHistory } from "react-router-dom";

const AddLocation = function () {
  const [errors, setErrors] = useState([]);
  useAuth();
  const history = useHistory();
  const addLocation = function (location) {
    // console.log(location);
    axios
      .post("/api/location", location)
      .then(function (response) {
        history.push("/");
      })
      .catch(function (error) {
        // alert(error.response.data);
        const newErrors = [];
        for (let message of error.response.data) {
          newErrors.push(message);
        }
        setErrors(newErrors);
        // console.log(error.response.data);
      });
  };

  const blankLocation = {
    locationId: 0,
    name: "",
    address: "",
  };
  return (
    <LocationForm
      defaultLocation={blankLocation}
      submitFcn={addLocation}
      formTitle="Add"
      errors={errors}
      setErrors={setErrors}
    />
  );
};

export default AddLocation;
