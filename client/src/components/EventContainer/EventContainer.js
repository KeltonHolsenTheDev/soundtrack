import React, { useState, useEffect } from "react";
import axios from "axios";
import "./EventContainer.css";
import EventCard from "../EventCard";
import EditEvent from "../EditEvent";
//import EventForm from "../EventForm";

const EventContainer = function () {
  const testEvents = [
    {
      eventId: 1,
      eventName: "Church Service",
      startDate: "0000-00-00",
      endDate: "0000-00-00",
      owner: {
        userId: 1,
        firstName: "First",
        lastName: "Last",
        email: "email@email.com",
        phone: "555-455-5555",
        accessLevel: "ROLE_ADMINISTRATOR",
        authorities: [
          {}
        ],
        password: "passwordpassword",
        roles: [
          "test_role"
        ]
      },
      ownerId: 1,
      staffIds: [
        1
      ],
      staffAndRoles: [
        { 
        user: {
          userId: 1,
        firstName: "First",
        lastName: "Last",
        email: "email@email.com",
        phone: "555-455-5555",
        accessLevel: "ROLE_ADMINISTRATOR",
        authorities: [
          {}
        ],
        password: "passwordpassword",
        roles: [
          "test_role"
        ]
        },
        roles: ["test_role"]
        }
      ],
      location: {
        locationId: 1,
        address: "123 Test Address",
        name: "Test Address"
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
          name: "Test Address"
        },
        locationDescription: "place",
        isBroken: false,
        notes: "no notes"
      }
    ],
    equipmentIds: [
      1
    ]
    }];

  const [events, setEvents] = useState([testEvents]);
  const [enableEdit, setEnableEdit] = useState(false);
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
        {events.map((event) => {
          return (
            <EventCard
              eventId={event.eventId}
              eventName={event.eventName}
              startDate={event.startDate}
              endDate={event.endDate}
              owner={event.owner}
              location={event.location}
              staffAndRoles={event.staffAndRoles}
              equipment={event.equipment}
              key={event.eventId}
              handleEdit={(event) => {
                handleEdit(event, event);
              }}
              handleDelete={(event) => {
                handleDelete(event, event);
              }}
            />
          );
        })}
      </tbody>
    </table>
  );
};

export default EventContainer;
