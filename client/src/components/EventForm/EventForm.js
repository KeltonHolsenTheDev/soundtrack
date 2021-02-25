import React, { useState, useEffect } from "react";
import "./EventForm.css";
import axios from "axios";

const EventForm = function ({
  defaultEvent,
  submitFcn,
  formtitle,
  errors,
  setErrors,
}) {
  const [eventName, setEventName] = useState(defaultEvent.eventName);
  const [startDate, setStartDate] = useState(defaultEvent.startDate);
  const [endDate, setEndDate] = useState(defaultEvent.endDate);
  const [users, setUsers] = useState([]);
  const [owner, setOwner] = useState(defaultEvent.owner.userId);
  const [staffAndRoles, setStaffAndRoles] = useState(
    defaultEvent.staffAndRoles
  );
  const [selectedStaff, setSelectedStaff] = useState({
    userId: 0,
    roles: [],
  });
  const [staffError, setStaffError] = useState(null);

  const [selectedRoles, setSelectedRoles] = useState([]);
  const [allLocations, setAllLocations] = useState([]);
  const [locationId, setLocationId] = useState([defaultEvent.locationId]);
  const [allItems, setAllItems] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);

  const [events, setEvents] = useState([]);

  useEffect(() => {
    axios.get("/api/location").then(function (response) {
      setAllLocations(response.data);
    });
    axios.get("/api/user").then(function (response) {
      setUsers(response.data);
    });
    axios.get("/api/item").then(function (response) {
      setAllItems(response.data);
      const itemIds = [];
      for (let item of response.data) {
        for (let equip of defaultEvent.equipment) {
          if (item.itemId == equip.itemId) {
            itemIds.push(item.itemId);
            break;
          }
        }
      }
      setSelectedItems(itemIds);
    });
    axios
      .get("/api/event")
      .then(function (response) {
        console.log(response.data);
        setEvents(response.data);
      })
      .catch(function (error) {
        console.log(error.response);
      });
  }, []);

  const handleSelectStaff = function (userId) {
    let selectedStaff;
    if (userId == 0) {
      return;
    }
    for (let user of users) {
      if (user.userId == userId) {
        selectedStaff = user;
        break;
      }
    }
    setSelectedStaff(selectedStaff);
  };

  const handleSelectRoles = function (selectedOptions) {
    const roles = [];
    for (let option of selectedOptions) {
      roles.push(option.value);
    }
    setSelectedRoles(roles);
  };

  const handleAddStaff = function (event) {
    event.preventDefault();
    if (selectedStaff.userId != 0 && selectedRoles.length > 0) {
      const newStaffAndRoles = [...staffAndRoles];
      let alreadyPresent = false;
      for (let userRole of newStaffAndRoles) {
        if (userRole?.user?.userId === selectedStaff.userId) {
          alreadyPresent = true;
          break;
        }
      }
      if (alreadyPresent) {
        setStaffError("That volunteer has already been added");
      } else {
        setStaffError(null);
        newStaffAndRoles.push({
          user: selectedStaff,
          roles: selectedRoles,
        });
      }

      setStaffAndRoles(newStaffAndRoles);
    }
  };

  const handleDeleteStaff = function (event, staffRole) {
    event.preventDefault();
    setStaffError(null);
    const newStaffAndRoles = [];
    for (let existingStaffRole of staffAndRoles) {
      if (existingStaffRole.user.userId != staffRole.user.userId) {
        newStaffAndRoles.push(existingStaffRole);
      }
    }
    setStaffAndRoles(newStaffAndRoles);
  };

  const handleSelectItems = function (selectedOptions) {
    const newSelectedItems = [];
    for (let selectedOption of selectedOptions) {
      newSelectedItems.push(selectedOption.value);
    }
    setSelectedItems(newSelectedItems);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setStaffError(null);
    const newErrors = [];
    const newEvent = {};
    let eventOwner = {};
    for (let user of users) {
      if (user.userId == owner) {
        eventOwner = user;
      }
    }
    let location = {};
    for (let loc of allLocations) {
      if (loc.locationId == locationId) {
        location = loc;
        break;
      }
    }
    const equipment = [];
    if (selectedItems == 0) {
      alert("You must select at least one item!");
      return;
    }
    for (let itemId of selectedItems) {
      for (let item of allItems) {
        if (item.itemId == itemId) {
          equipment.push(item);
          break;
        }
      }
    }

    if (newErrors.length > 0) {
      setErrors(newErrors);
    } else {
      newEvent.eventId = defaultEvent.eventId;
      newEvent.eventName = eventName;
      newEvent.startDate = startDate;
      newEvent.endDate = endDate;
      newEvent.owner = eventOwner;
      newEvent.staffAndRoles = staffAndRoles;
      newEvent.location = location;
      newEvent.equipment = equipment;
    }
    submitFcn(newEvent);
    // console.log(newEvent);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col ">
          <div className="card mt-5">
            <div className="card-body">
              <h1 className="card-title text-center mb-2">{formtitle}</h1>
              {errors.map((error) => (
                <p
                  key={errors.indexOf(error)}
                  className="card-text error-event-form"
                >
                  {error}
                </p>
              ))}
              {/* Start of form */}
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="eventName">Name</label>
                  <input
                    type="text"
                    className="form-control"
                    id="eventName"
                    aria-describedby="eventName"
                    required
                    placeholder="event name"
                    value={eventName}
                    onChange={(e) => setEventName(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="startDate">Start Date</label>
                  <input
                    type="date"
                    className="form-control"
                    id="startDate"
                    aria-describedby="startDate"
                    placeholder="yyyy-mm-dd"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="endDate">End Date</label>
                  <input
                    type="date"
                    className="form-control"
                    id="endDate"
                    aria-describedby="endDate"
                    placeholder="yyyy-mm-dd"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="eventOwner">Owner</label>
                  <select
                    className="custom-select"
                    id="eventOwner"
                    value={owner}
                    onChange={(e) => setOwner(e.target.value)}
                    required
                  >
                    <option value="">
                      Open to select an owner for the event
                    </option>
                    {users.map((user) => (
                      <option
                        value={user.userId}
                        key={user.userId}
                      >{`${user.firstName} ${user.lastName}`}</option>
                    ))}
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="eventStaff" className="mb-0">
                    Staff and Roles
                  </label>
                  {staffError ? (
                    <p className="card-text error-event-form">{staffError}</p>
                  ) : (
                    ""
                  )}
                  {staffAndRoles.map((staffRole) => {
                    return (
                      <>
                        <p key={staffRole.user.userId} className="mb-0 mt-2">
                          {`${staffRole.user.firstName} ${staffRole.user.lastName}`}
                          <button
                            type="button"
                            className="close"
                            aria-label="Close"
                            onClick={(e) => handleDeleteStaff(e, staffRole)}
                          >
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </p>
                        {staffRole.roles.map((role) => {
                          return (
                            <>
                              <small
                                key={staffRole.roles.indexOf(role)}
                                required
                              >
                                {role}
                              </small>
                              <br />
                            </>
                          );
                        })}
                      </>
                    );
                  })}
                  <select
                    className="custom-select mt-3 mb-1"
                    id="eventStaff"
                    value={selectedStaff?.userId}
                    onChange={(e) => handleSelectStaff(e.target.value)}
                    required
                  >
                    <option value="0">Select a staff member</option>
                    {users.map((user) => {
                      let busy = false;
                      for (let e of events) {
                        if (e.staffAndRoles != undefined) {
                          for (let u of e.staffAndRoles) {
                            if (u?.user?.userId === user?.userId && ((new Date(e?.startDate) >= new Date(startDate) && new Date(e?.startDate) <= new Date(endDate)) 
                              || (new Date(e?.endDate) >= new Date(startDate) && new Date(e?.endDate) <= new Date(endDate)))) {
                              busy = true;
                              break;
                            }
                          }
                        }
                        
                      }
                      if (busy) {
                        return (
                          <option
                          key={user.userId}
                          value={user.userId} disabled
                        >{`${user.firstName} ${user.lastName}`}</option>
                        );
                      } 
                      else {
                        return (
                          <option
                            key={user.userId}
                            value={user.userId}
                          >{`${user.firstName} ${user.lastName}`}</option>
                        );
                      }  
                    })}
                  </select>
                  <select
                    className="custom-select mb-1"
                    multiple={true}
                    value={selectedRoles}
                    required
                    onChange={(e) =>
                      handleSelectRoles(e.target.selectedOptions)
                    }
                  >
                    {selectedStaff?.roles.map((role) => {
                      return (
                        <option
                          value={role}
                          key={selectedStaff.roles.indexOf(role)}
                        >
                          {role}
                        </option>
                      );
                    })}
                  </select>
                  <button
                    type="button"
                    className="btn btn-info"
                    onClick={(e) => handleAddStaff(e)}
                  >
                    Add staff member
                  </button>
                </div>
                <div className="form-group">
                  <label htmlFor="eventLocation">Event Location</label>
                  <select
                    class="form-control"
                    id="eventLocation"
                    required
                    value={locationId}
                    onChange={(e) => {
                      if (e.target.value > 0) {
                        setLocationId(e.target.value);
                      }
                    }}
                  >
                    <option value={0}>Select a location</option>
                    {allLocations.map((location) => {
                      let busy = false;
                      for (let e of events) {
                        if (e.equipment != undefined) {
                          if (e.locationId === location.locationId && ((new Date(e?.startDate) >= new Date(startDate) && new Date(e?.startDate) <= new Date(endDate)) 
                              || (new Date(e?.endDate) >= new Date(startDate) && new Date(e?.endDate) <= new Date(endDate)))) {
                              busy = true;
                              break;
                            }
                        }
                        
                      }
                      if (busy) {
                        return (
                          <option value={location.locationId} key={location.locationId} disabled>
                            {location.name}
                          </option>
                        );
                      } 
                      else {
                        return (
                          <option value={location.locationId} key={location.locationId}>
                            {location.name}
                          </option>
                        );
                      }
                    })}
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="eventEquipment">Event Equipment (items used by another event at the same time will not show up)</label>
                  <select
                    className="custom-select mb-1"
                    id="eventEquipment"
                    multiple={true}
                    value={selectedItems}
                    onChange={(e) =>
                      handleSelectItems(e.target.selectedOptions)
                    }
                  >
                    {allItems.map((item) => {
                      if (!item.broken) {
                        let busy = false;
                        for (let e of events) {
                          if (e.equipment != undefined) {
                            for (let i of e.equipment) {
                              if (i?.itemId === item?.itemId && ((new Date(e?.startDate) >= new Date(startDate) && new Date(e?.startDate) <= new Date(endDate)) 
                                || (new Date(e?.endDate) >= new Date(startDate) && new Date(e?.endDate) <= new Date(endDate)))) {
                                busy = true;
                                break;
                              }
                            }
                          }
                          
                        }
                        if (!busy) {
                          return (
                            <option value={item.itemId} key={item.itemId}>
                              {item.itemName}
                            </option>
                          );
                        } 
                      }
                    })}
                  </select>
                </div>

                <button
                  type="submit"
                  className="btn btn-primary"
                  // onClick={(e) => handleSubmit(e)}
                >
                  Submit
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default EventForm;
