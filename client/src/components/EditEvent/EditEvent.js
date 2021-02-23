import React from "react";
import "./EditEvent";
import EventForm from "../EventForm";
import axios from "axios";

const EditEvent = function ({ chosenEvent, setEnableEdit, renderEvents }) {
  const editEvent = function (updatedEvent) {
    // console.log(updatedEvent);
    axios
      .put(`/api/event/${chosenEvent.eventId}`, updatedEvent)
      .then(function (response) {
        setEnableEdit(false);
        renderEvents();
      })
      .catch(function (error) {
        alert(error.response.data[0].defaultMessage);
      });
  };

  return (
    //<button src="btn btn-primary">Update Placeholder</button>
    <EventForm
      defaultEvent={chosenEvent}
      submitFcn={editEvent}
      formTitle="Update"
    />
  );
};
export default EditEvent;
