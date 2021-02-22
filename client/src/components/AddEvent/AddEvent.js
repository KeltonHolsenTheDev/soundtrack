import React from "react";
import "./AddEvent.css";
import EventForm from "../EventForm";
import { useHistory } from "react-router-dom";
import axios from "axios";

const AddEvent = function () {
  const history = useHistory();

  const blankEvent = {
    eventName: "",
    startDate: "",
    endDate: "",
    owner: {
      id: 0,
      firstName: "",
      lastName: "",
      phone: "",
      email: "",
      accessLevel: "",
      password: "",
      roles: [],
    },
    staffAndRoles: [],
    location: null,
    equipment: [],
  };

  const addEvent = function (newEvent) {
    axios
      .post("/api/event", newEvent)
      .then(function (response) {
        history.push("/");
      })
      .catch(function (error) {
        console.log(error.response.data);
      });
  };

  return <EventForm defaultEvent={blankEvent} submitFcn={addEvent} />;
};
export default AddEvent;
