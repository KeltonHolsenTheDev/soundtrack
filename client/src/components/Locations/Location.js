import React, { useState, useContext, useEffect } from "react";
import { AuthContext } from "../../auth/auth";
import axios from "axios";
import { useHistory } from "react-router-dom";
import LocationCard from "./LocationCard";

import "./Location.css";

const Location = function () {
  const { setLocation } = useContext(AuthContext);
  const history = useHistory();
  const blankLocation = {
    id: 0,
    address: "",
    name: "",
  };

  const [newLocation, setNewLocation] = useState(blankLocation);
  const [locations, setLocations] = useState([]);

  useEffect(() => {
    axios.get("/api/location").then(
      (response) => {
        setLocations(response.data);
      },
      (error) => {
        console.log(error);
      }
    );
  }, []);

  const onChangeHandler = (event) => {
    const updatedLocation = { ...newLocation };
    updatedLocation[event.target.name] = event.target.value;

    setNewLocation(updatedLocation);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    axios
      .post("/api/location", newLocation)
      .then(function (response) {
        console.log(response.data);
        setLocations([...locations, response.data]);
      })
      .catch(function (error) {
        console.log(error.response);
        if (error.response == undefined) {
          return;
        }
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
    console.log(`editing location ${locationId}`);
    axios
      .put(`/api/location/${locationId}`, location)
      .then(function (response) {
        console.log(response.data);
        let newLocations = [...locations];
        for (let i = 0; i < newLocations.length; i++) {
          if (locations[i].locationId == locationId) {
            newLocations.splice(i, 1, location);
          }
        }
        setLocations([...newLocations]);
      })
      .catch(function (error) {
        console.log(error.response);
        if (error.response == undefined) {
          return;
        }
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
        setLocations(locations.filter((l) => l.locationId != locationId));
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
              <h1 className="card-title mb-3 text-center">
                Location Add/Update
              </h1>
              <form onSubmit={handleSubmit}>
                <div className="form-group register-form-group">
                  <label htmlFor="formGroupExampleInput">Location Name</label>
                  <input
                    type="text"
                    name="name"
                    className="form-control"
                    id="formGroupExampleInput"
                    placeholder="Bart's House"
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
                    placeholder="742 Evergreen Terrace"
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
      <div className="container">
        <div className="row mt-5">
          <table className="table table-hover table-light table-striped thead-dark">
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
                      let loc = newLocation;
                      loc.locationId = location.locationId;
                      handleEdit(event, loc, location.locationId);
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
    </div>
  );
};
export default Location;
