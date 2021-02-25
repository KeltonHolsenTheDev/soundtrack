import React, { useState } from "react";
import "./AddEvent.css";
import EventForm from "../EventForm";
import { useHistory } from "react-router-dom";
import { useAuth } from "../../auth/auth";
import axios from "axios";

const AddEvent = function () {
  const [errors, setErrors] = useState([]);
  useAuth();
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
        const newErrors = [];
        for (let message of error.response.data) {
          // errorMessage += message + "\n";
          newErrors.push(message);
        }
        setErrors(newErrors);
        // alert(newErrors);
        // console.log(error.response.data);

        // alert(error.response.data);
        // console.log(error.response.data);
      });
  };

  return (
    <EventForm
      defaultEvent={blankEvent}
      submitFcn={addEvent}
      formtitle="Add Event"
      errors={errors}
      setErrors={setErrors}
    />
  );
};
export default AddEvent;
