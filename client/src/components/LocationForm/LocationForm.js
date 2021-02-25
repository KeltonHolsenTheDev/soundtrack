import React, { useState, useEffect } from "react";
import "./LocationForm.css";
import axios from "axios";
import { CSSTransition } from "react-transition-group";

const LocationForm = function ({
  defaultLocation,
  submitFcn,
  formTitle,
  errors,
  setErrors,
}) {
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
    const newErrors = [];
    // if (name === "") {
    //   newErrors.push("Name cannot be blank");
    // }
    if (address === "") {
      newErrors.push("Address cannot be blank");
    }
    if (newErrors.length > 0) {
      setErrors(newErrors);
    } else {
      const location = {
        locationId: defaultLocation.locationId,
        name: name,
        address: address,
      };

      submitFcn(location);
      // console.log(location);
      // console.log(submitFcn);
    }
  };

  return (
    <div className="container location-form-container">
      <div className="container location-form">
        <div className="row ">
          <div class="col-4 d-none d-md-block location-col-left">JAK</div>
          <div className="col location-form-col">
            <div className="card shadow location-form-card">
              <div className="card-body location-form-cardBody">
                <h1 className="card-title mb-3 text-center">
                  {`${formTitle} Location `}
                </h1>
                {errors.map((error) => (
                  <p
                    key={errors.indexOf(error)}
                    className="card-text error-location-form"
                  >
                    {error}
                  </p>
                ))}

                <form onSubmit={handleSubmit}>
                  <div className="form-group">
                    <label htmlFor="formGroupExampleInput">Name</label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput"
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
                    <div className="col-10">
                      <button
                        // onClick={handleSubmit}
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
