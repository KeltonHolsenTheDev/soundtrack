import React, { useState, useContext } from "react";
import "./EventCard.css";
import { AuthContext } from "../../auth/auth";

const EventCard = function (props) {
  const { user } = useContext(AuthContext);
  const buttonsAvailable =
    user && (user.access === "ROLE_ADMINISTRATOR" || user.sub === props.email);
  return (
    <div class="card">
      <img
        src="https://picsum.photos/id/1082/200/300"
        class="card-img-top"
        alt="..."
      />
      <div class="card-body">
        <h5 class="card-title">Card title</h5>
        <p class="card-text">
          Some quick example text to build on the card title and make up the
          bulk of the card's content.
        </p>
      </div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item">Cras justo odio</li>
        <li class="list-group-item">Dapibus ac facilisis in</li>
        <li class="list-group-item">Vestibulum at eros</li>
      </ul>
      <div class="card-body">
        <a href="#" class="card-link">
          Card link
        </a>
        <a href="#" class="card-link">
          Another link
        </a>
      </div>
    </div>
  );
};
export default EventCard;
