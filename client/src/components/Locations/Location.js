import React, { useState, useContext } from "react";
import { AuthContext } from "../../auth/auth";
import axios from "axios";
import { useHistory } from "react-router-dom";
import LocationCard from "./LocationCard";

import ".Location.css";

const Location = function () {
  const { setLocation } = useContext(AuthContext);
  const history = useHistory();
  const blankLocation = {
    id: 0,
    address="",
    name=""
  };

  const [newLocation, setNewLocation] = useState(blankLocation);

  const onChangeHandler = (event) => {
    const updatedLocation = { ...newLocation };
    updatedLocation[event.target.name] = event.target.value;

    setNewLocation(updatedLocation);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    axios
        .post("/api/Location", newLocation)
        .then(function (response) {
          setLocation(response.data);
          history.push("/");
        })
        .catch(function (error) {
          console.log(error.response);
          for (let message of error.response.data) {
            if (message.defaultMessage) {
              alert(message.defaultMessage);
            } else {
              alert(message);
            }
          }
        });
  };

  const handleEdit = function (event, location, locationId) {
    event.preventDefault();
    console.log(`editing location ${user.locationId}`);
    axios
      .put(`/api/location/${locationId}`, location)
      .then(function (response) {
        setLocation(response.data);
        history.push("/");
      })
      .catch(function (error) {
        console.log(error.response);
          for (let message of error.response.data) {
            if (message.defaultMessage) {
              alert(message.defaultMessage);
            } else {
              alert(message);
            }
          }
      });
  };

  const handleDelete = function (event, locationId) {
    event.preventDefault();
    console.log(`deleting location ${locationId}`);
    axios
      .delete(`/api/location/${locationId}`)
      .then(function (response) {
        history.push("/");
      })
      .catch(function (error) {
        console.log(error.response);
          for (let message of error.response.data) {
            if (message.defaultMessage) {
              alert(message.defaultMessage);
            } else {
              alert(message);
            }
          }
      });
  };

  return (
    <div className="container-fluid register-background">
      <div className="row ">
        <div className="col-3"></div>
        <div className="col-6 register-card-container d-flex mt-3">
          <div className="card shadow-lg px-4 w-100 rounded-0 register-card">
            <div className="card-body register-body">
              <h1 className="card-title mb-3 text-center">Add Location to Database</h1>
              <form onSubmit={handleSubmit}>
                <div className="form-group register-form-group">
                  <label htmlFor="formGroupExampleInput">Location Name</label>
                  <input
                    type="text"
                    name="name"
                    className="form-control"
                    id="formGroupExampleInput"
                    placeholder="Oz Church"
                    onChange={onChangeHandler}
                    value={newLocation.name}
                  />
                </div>
                <div className="form-group register-form-group">
                  <label htmlFor="formGroupExampleInput2">Address</label>
                  <input
                    type="text"
                    name="address"
                    className="form-control"
                    id="formGroupExampleInput2"
                    placeholder="123 Corn Street"
                    onChange={onChangeHandler}
                    value={newLocation.address}
                    required
                  />
                </div>
                <div className="form-group register-form-group row">
                  <div className="col-sm-10">
                    <button type="submit" className="btn btn-primary rounded-0">
                      Add Location
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div className="col-3"></div>
      </div>
      <div className="row">
      <table className="table table-hover table-striped">
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
                handleEdit={(event) => {
                  handleEdit(event, location);
                }}
                handleDelete={(event) => {
                  handleDelete(event, location.locationId);
                }}
              />
            );
          })}
        </tbody>
      </table>
      </div>
    </div>
  );
};
export default Location;
