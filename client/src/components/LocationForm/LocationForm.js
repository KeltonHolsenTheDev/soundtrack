import React, { useState, useEffect } from "react";
import "./LocationForm.css";
import axios from "axios";

const LocationForm = function ({ defaultLocation, submitFcn, formTitle }) {
  // const [newLocation, setNewLocation] = useState(starterLocation);
  const [name, setName] = useState(defaultLocation.name);
  const [address, setAddress] = useState(defaultLocation.address);


  // const onChangeHandler = (event) => {
  //   const updatedLocation = { ...newLocation };
  //   updatedLocation[event.target.name] = event.target.value;
  //   setNewLocation(updatedLocation);
  // };

  const handleSubmit = (event) => {
    event.preventDefault();
    const location = {
      locationId: defaultLocation.locationId,
      name: name,
      address: address
    };

    submitFcn(location);
    // console.log(location);
    // console.log(submitFcn);
  };

  return (
    <div className="container location-form-container">
      <div className="container location-form">
        <div className="row">
          <div className="col">
            <div className="card shadow-lg location-form-card">
              <div className="card-body location-form-cardBody">
                <h1 className="card-title mb-3 text-center">
                  {`${formTitle} Location `}
                </h1>
                <form>
                  <div className="form-group">
                    <label htmlFor="formGroupExampleInput">Name</label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="Name"
                      name="name"
                      onChange={(e) => setName(e.target.value)}
                      value={name}
                      required
                    />
                  </div>
                  <div class="form-group">
                    <label htmlFor="formGroupExampleInput2">Address</label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="Address"
                      name="address"
                      onChange={(e) => setAddress(e.target.value)}
                      value={address}
                      required
                    />
                  </div>

                  <div className="form-group userform-form-group row">
                    <div className="col-sm-10">
                      <button
                        onClick={handleSubmit}
                        type="submit"
                        className="btn btn-primary rounded-0 mt-3"
                      >
                        {formTitle}
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default LocationForm;
