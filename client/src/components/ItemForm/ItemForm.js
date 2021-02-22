import React, { useState, useEffect } from "react";
import "./ItemForm.css";
import axios from "axios";

const ItemForm = function ({ defaultItem, submitFcn, formTitle }) {
  // const [newItem, setNewItem] = useState(starterItem);
  const [name, setName] = useState(defaultItem.itemName);
  const [description, setDescription] = useState(defaultItem.description);
  const [brand, setBrand] = useState(defaultItem.brand);
  const [itemType, setItemType] = useState(defaultItem.itemType);
  const [category, setCategory] = useState(defaultItem.itemCategory);
  const [allLocations, setAllLocations] = useState([]);
  const [locationId, setLocationId] = useState(defaultItem.locationId);
  const [locationDescription, setLocationDescription] = useState(
    defaultItem.locationDescription
  );
  const [checked, setChecked] = useState(defaultItem.broken);
  const [notes, setNotes] = useState(defaultItem.notes);

  useEffect(() => {
    axios.get("/api/location").then(function (response) {
      setAllLocations(response.data);
    });
  }, []);

  // const onChangeHandler = (event) => {
  //   const updatedItem = { ...newItem };
  //   updatedItem[event.target.name] = event.target.value;
  //   setNewItem(updatedItem);
  // };

  // const chooseLocation = (event) => {
  //   let location = {};
  //   for (let loc of allLocations) {
  //     if (loc.locationId == event.target.value) {
  //       location = loc;
  //       break;
  //     }
  //   }
  //   setChosenLocation(location);
  // };

  const handleSubmit = (event) => {
    event.preventDefault();
    let location = {};
    for (let loc of allLocations) {
      if (loc.locationId == locationId) {
        location = loc;
        break;
      }
    }
    const item = {
      itemId: defaultItem.itemId,
      itemName: name,
      description: description,
      brand: brand,
      itemType: itemType,
      itemCategory: category,
      location: location,
      locationId: locationId,
      locationDescription: locationDescription,
      broken: checked,
      notes: notes
    };
    // item.location = chosenLocation;
    // item.locationId = chosenLocation.locationId;
    submitFcn(item);
    // console.log(item);
    // console.log(submitFcn);
  };

  return (
    <div className="container item-form-container">
      <div className="container item-form">
        <div className="row">
          <div className="col">
            <div className="card shadow-lg item-form-card">
              <div className="card-body item-form-cardBody">
                <h1 className="card-title mb-3 text-center">
                  {`${formTitle} Item `}
                </h1>
                <form>
                  <div className="form-group">
                    <label htmlFor="formGroupExampleInput">Name</label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="Name"
                      name="itemName"
                      onChange={(e) => setName(e.target.value)}
                      value={name}
                      required
                    />
                  </div>
                  <div class="form-group">
                    <label htmlFor="formGroupExampleInput2">Description</label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="description"
                      name="description"
                      onChange={(e) => setDescription(e.target.value)}
                      value={description}
                      required
                    />
                  </div>
                  <div class="form-group">
                    <label htmlFor="formGroupExampleInput2">Brand</label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="Brand"
                      name="brand"
                      onChange={(e) => setBrand(e.target.value)}
                      value={brand}
                      required
                    />
                  </div>
                  <div class="form-group">
                    <label htmlFor="formGroupExampleInput2">Type</label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="Type"
                      name="itemType"
                      onChange={(e) => setItemType(e.target.value)}
                      value={itemType}
                      required
                    />
                  </div>

                  <label htmlFor="itemLocation">Category</label>
                  <select
                    // multiple
                    class="form-control"
                    id="exampleFormControlSelect2"
                    required
                    name="itemCategory"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                  >
                    <option value="OTHER">Other</option>
                    <option value="VIDEO">Video</option>
                    <option value="LIGHTING">Lighting</option>
                    <option value="AUDIO">Audio</option>
                  </select>
                  <label htmlFor="itemLocation">Current Location</label>
                  <select
                    // multiple
                    class="form-control"
                    id="exampleFormControlSelect2"
                    required
                    value={locationId}
                    onChange={(e) => {
                      console.log(e.target.value);
                      setLocationId(e.target.value);
                    }}
                  >
                    {allLocations.map((location) => {
                      // console.log(newItem);
                      return (
                        <option
                          key={location.locationId}
                          value={location.locationId}
                        >
                          {`${location.name}: ${location.address}`}
                        </option>
                      );
                    })}
                  </select>
                  <div class="form-group">
                    <label htmlFor="formGroupExampleInput2">
                      Location Description
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="Location Description"
                      name="locationDescription"
                      onChange={(e) => setLocationDescription(e.target.value)}
                      value={locationDescription}
                      required
                    />
                  </div>
                  <div class="custom-control custom-checkbox">
                    <input
                      type="checkbox"
                      class="custom-control-input"
                      id="customCheck1"
                      checked={checked}
                      onChange={(e) => setChecked(e.target.checked)}
                    />
                    <label class="custom-control-label" for="customCheck1">
                      Check if Item needs repair
                    </label>
                  </div>

                  <div class="form-group">
                    <label htmlFor="formGroupExampleInput2">Notes</label>
                    <textarea
                      type="textarea"
                      className="form-control"
                      id="formGroupExampleInput2"
                      placeholder="Notes"
                      name="notes"
                      onChange={(e) => setNotes(e.target.value)}
                      value={notes}
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
export default ItemForm;
