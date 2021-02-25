import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../../auth/auth";
import "./EventContainer.css";
import EventCard from "../EventCard";
import EditEvent from "../EditEvent";
import EventForm from "../EventForm";
import image1 from "../../img/audio-868487_1280.jpg";
import image2 from "../../img/brickwall_@2X.png";
import image3 from "../../img/dark_brick_wall.png";

const EventContainer = function ({ enableEdit, setEnableEdit }) {
  useAuth();
  const testEvents = [
    {
      eventId: 1,
      eventName: "Event Name Here",
      startDate: "0000-00-00",
      endDate: "0000-00-00",
      owner: {
        userId: 1,
        firstName: "First",
        lastName: "Last",
        email: "email@email.com",
        phone: "555-455-5555",
        accessLevel: "ROLE_ADMINISTRATOR",
        authorities: [{}],
        password: "passwordpassword",
        roles: ["test_role"],
      },
      ownerId: 1,
      staffIds: [1],
      staffAndRoles: [
        {
          user: {
            userId: 1,
            firstName: "First",
            lastName: "Last",
            email: "email@email.com",
            phone: "555-455-5555",
            accessLevel: "ROLE_ADMINISTRATOR",
            authorities: [{}],
            password: "passwordpassword",
            roles: ["test_role"],
          },
          roles: ["test_role"],
        },
      ],
      location: {
        locationId: 1,
        address: "123 Test Address",
        name: "Test Address",
      },
      locationId: 1,
      equipment: [
        {
          itemId: 1,
          itemName: "Test Item",
          description: "Test Item",
          brand: "Test",
          itemType: "test item",
          itemCategory: "AUDIO",
          locationId: 1,
          location: {
            locationId: 1,
            address: "123 Test Address",
            name: "Test Address",
          },
          locationDescription: "place",
          isBroken: false,
          notes: "no notes",
        },
      ],
      equipmentIds: [1],
    },
  ];

  //TODO: replace images
  const images = [image1, image2, image3];

  const [events, setEvents] = useState([testEvents]);
  const [chosenEvent, setChosenEvent] = useState(null);

  const renderEvents = function () {
    axios
      .get("/api/event")
      .then(function (response) {
        console.log(response.data);
        setEvents(response.data);
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  useEffect(() => {
    renderEvents();
  }, []);

  const handleEdit = function (event, editedEvent) {
    event.preventDefault();
    setChosenEvent(editedEvent);
    setEnableEdit(true);
  };

  const handleDelete = function (event, deletedEvent) {
    event.preventDefault();
    if (!window.confirm("Are you sure?")) {
      return;
    }
    axios
      .delete(`/api/event/${deletedEvent.eventId}`)
      .then(function (response) {
        // setEvents(events.filter((i) => i.eventId != deletedEvent.eventId));
        renderEvents();
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  return enableEdit ? (
    <EditEvent
      chosenEvent={chosenEvent}
      setEnableEdit={setEnableEdit}
      renderEvents={renderEvents}
    />
  ) : (
    <table className="table table-hover table-striped">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Event Name</th>
          <th scope="col">Start Date</th>
          <th scope="col">End Date</th>
          <th scope="col">Owner</th>
          <th scope="col">Location</th>
          <th scope="col">Staff</th>
          <th scope="col">Equipment</th>
          <th scope="col"></th>
        </tr>
      </thead>
      <tbody>
        {events.map((event_) => {
          return (
            <EventCard
              eventId={event_.eventId}
              eventName={event_.eventName}
              startDate={event_.startDate}
              endDate={event_.endDate}
              owner={event_.owner}
              location={event_.location}
              staffAndRoles={event_.staffAndRoles}
              equipment={event_.equipment}
              key={event_.eventId}
              handleEdit={(event) => {
                handleEdit(event, event_);
              }}
              handleDelete={(event) => {
                handleDelete(event, event_);
              }}
              image={images[Math.floor(Math.random() * images.length)]}
            />
          );
        })}
      </tbody>
    </table>
  );
};

export default EventContainer;
