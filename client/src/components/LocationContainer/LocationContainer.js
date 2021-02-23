import React, { useState, useEffect } from "react";
import axios from "axios";
import "./LocationContainer.css";
import LocationCard from "../LocationCard";
import EditLocation from "../EditLocation";
// import LocationForm from "../LocationForm";

const LocationContainer = function ({ enableEdit, setEnableEdit }) {
  const testLocations = [
    {
      locationId: 1,
      name: "test location",
      address: "123 placeholder street",
    },
  ];

  const [locations, setLocations] = useState([testLocations]);
  const [chosenLocation, setChosenLocation] = useState(null);

  const renderLocations = function () {
    axios
      .get("/api/location")
      .then(function (response) {
        console.log(response.data);
        setLocations(response.data);
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  useEffect(() => {
    renderLocations();
  }, []);

  const handleEdit = function (event, editedLocation) {
    event.preventDefault();
    setChosenLocation(editedLocation);
    setEnableEdit(true);
  };

  const handleDelete = function (event, deletedLocation) {
    event.preventDefault();
    if (!window.confirm("CONFIRM LOCATION DELETION:\n WARNING: Deleting a location will delete all associated events and items!")) {
      return;
    }
    axios
      .delete(`/api/location/${deletedLocation.locationId}`)
      .then(function (response) {
        // setLocations(locations.filter((i) => i.locationId != deletedLocation.locationId));
        renderLocations();
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  return enableEdit ? (
    <EditLocation
      chosenLocation={chosenLocation}
      setEnableEdit={setEnableEdit}
      renderLocations={renderLocations}
    />
  ) : (
    <table className="table table-hover table-striped text-center">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Address</th>
          <th scope="col"></th>
        </tr>
      </thead>
      <tbody>
        {locations.map((location) => {
          return (
            <LocationCard
              locationId={location.locationId}
              name={location.name}
              address={location.address}
              key={location.locationId}
              handleEdit={(event) => {
                handleEdit(event, location);
              }}
              handleDelete={(event) => {
                handleDelete(event, location);
              }}
            />
          );
        })}
      </tbody>
    </table>
  );
};

export default LocationContainer;
