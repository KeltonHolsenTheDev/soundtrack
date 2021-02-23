import React from "react";
import "./EditLocation.css";
import LocationForm from "../LocationForm";
import axios from "axios";

const EditLocation = function ({ chosenLocation, setEnableEdit, renderLocations }) {
  const editLocation = function (updatedLocation, history) {
    axios
      .put(`/api/location/${chosenLocation.locationId}`, updatedLocation)
      .then(function (response) {
        setEnableEdit(false);
        renderLocations();
      })
      .catch(function (error) {
        alert(error.response.data[0].defaultMessage);
      });
  };

  return (
    <LocationForm
      defaultLocation={chosenLocation}
      submitFcn={editLocation}
      formTitle="Update"
    />
  );
};
export default EditLocation;
