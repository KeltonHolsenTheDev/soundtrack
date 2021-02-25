import React, { useState } from "react";
import "./EditEvent";
import EventForm from "../EventForm";
import axios from "axios";

const EditEvent = function ({ chosenEvent, setEnableEdit, renderEvents }) {
  const [errors, setErrors] = useState([]);
  const editEvent = function (updatedEvent) {
    axios
      .put(`/api/event/${chosenEvent.eventId}`, updatedEvent)
      .then(function (response) {
        setEnableEdit(false);

        renderEvents();
      })
      .catch(function (error) {
        const newErrors = [];
        for (let message of error.response.data) {
          newErrors.push(message.defaultMessage);
        }
        setErrors(newErrors);
        // alert(error.response.data[0].defaultMessage);
      });
  };

  return (
    //<button src="btn btn-primary">Update Placeholder</button>
    <EventForm
      defaultEvent={chosenEvent}
      submitFcn={editEvent}
      formTitle="Update"
      errors={errors}
      setErrors={setErrors}
    />
  );
};
export default EditEvent;
