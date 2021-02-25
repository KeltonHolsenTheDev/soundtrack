import React, { useState } from "react";
import "./EditLocation.css";
import LocationForm from "../LocationForm";
import axios from "axios";

const EditLocation = function ({
  chosenLocation,
  setEnableEdit,
  renderLocations,
}) {
  const [errors, setErrors] = useState([]);

  const editLocation = function (updatedLocation, history) {
    axios
      .put(`/api/location/${chosenLocation.locationId}`, updatedLocation)
      .then(function (response) {
        setEnableEdit(false);
        renderLocations();
      })
      .catch(function (error) {
        const newErrors = [];
        for (let message of error.response.data) {
          newErrors.push(message);
        }
        console.log(error.response.data);
        setErrors(newErrors);
        // alert(error.response.data[0].defaultMessage);
      });
  };

  return (
    <LocationForm
      defaultLocation={chosenLocation}
      submitFcn={editLocation}
      formTitle="Update"
      errors={errors}
      setErrors={setErrors}
    />
  );
};
export default EditLocation;
