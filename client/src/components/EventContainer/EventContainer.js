import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../../auth/auth";
import "./EventContainer.css";
import EventCard from "../EventCard";
import EditEvent from "../EditEvent";
import EventForm from "../EventForm";
import image1 from "../../img/event-1.jpg";
import image2 from "../../img/event-2.jpg";
import image3 from "../../img/event-3.jpg";
import image4 from "../../img/event-4.jpg";
import image5 from "../../img/event-5.jpg";
import image6 from "../../img/event-6.jpg";
import image7 from "../../img/event-7.jpg";

import ReactCardFlip from "react-card-flip";

const EventContainer = function ({ enableEdit, setEnableEdit }) {
  useAuth();
  const testEvents = [
    {
      eventId: 0,
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
      ownerId: 0,
      staffIds: [0],
      staffAndRoles: [
        {
          user: {
            userId: 0,
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
        locationId: 0,
        address: "123 Test Address",
        name: "Test Address",
      },
      locationId: 0,
      equipment: [
        {
          itemId: 0,
          itemName: "Test Item",
          description: "Test Item",
          brand: "Test",
          itemType: "test item",
          itemCategory: "AUDIO",
          locationId: 1,
          location: {
            locationId: 0,
            address: "123 Test Address",
            name: "Test Address",
          },
          locationDescription: "place",
          isBroken: false,
          notes: "no notes",
        },
      ],
      equipmentIds: [0],
    },
  ];

  //TODO: replace images
  const images = [image1, image2, image3, image4, image5, image6, image7];

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
  const [isFlipped, setIsFlipped] = useState(false);
  const handleFlip = function () {
    setIsFlipped(true);
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
    <ReactCardFlip isFlipped={isFlipped} flipDirection="vertical">
      <EditEvent
        chosenEvent={chosenEvent}
        setEnableEdit={setEnableEdit}
        renderEvents={renderEvents}
      />
    </ReactCardFlip>
  ) : (
    <div class="container">
      <h1>Events</h1>
      <div class="row">
        {events.map((event_) => {
          
          if (event_.eventId > 0) {
            return (
              <div class="col-12 col-sm-6 col-lg-4 mb-3 text-center">
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
                handleFlip={(e) => {
                  handleFlip();
                  console.log("you got clicked", handleFlip);
                }}
                image={images[Math.floor(Math.random() * images.length)]}
              />
            </div>
          );
        }
        })}
      </div>
    </div>
  );
};

export default EventContainer;
