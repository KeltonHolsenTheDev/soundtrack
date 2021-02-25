import React, { useContext, useState } from "react";
import "./EventCard.css";
import { AuthContext } from "../../auth/auth";
import ReactCardFlip from "react-card-flip";

const EventCard = function (props) {
  const { user } = useContext(AuthContext);

  const buttonsAvailable =
    user &&
    (user.access === "ROLE_ADMINISTRATOR" || user.sub === props.owner?.email);

  const owner = props.owner;
  const location = props.location;
  const staffAndRoles = props.staffAndRoles;
  const equipment = props.equipment;

  return (
    <div class="card mt-3">
      <div class="card-body text-center">
        <h5 class="card-title event-front">{`${props.eventId}: ${props.eventName}`}</h5>
        <img src={props.image} alt="" className="event-img" />
      </div>
      <div>
        <p className="card-text">
          <b>Start Date: </b>
          {props.startDate}
        </p>
        <p className="card-text">
          <b>End Date: </b>
          {props.endDate}
        </p>
      </div>
      <div>
        <ul class="list-group list-group-flush event-back">
          <li class="list-group-item">
            <b>Owner:</b> {owner?.lastName}
          </li>
          <li class="list-group-item">
            <b>Location: </b> {location?.name}
          </li>
          <li class="list-group-item">
            <b className="">Staff:</b>

            <table className="table table-hover event-table ">
              <thead>
                <tr>
                  <th scope="col">Volunteer:</th>
                  <th scope="col">Role:</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                {staffAndRoles?.map((userRole) => {
                  console.log(userRole?.roles);
                  return (
                    <tr>
                      <th scope="col">{userRole?.user?.lastName}</th>
                      <th scope="col">
                        {userRole?.roles?.map((role) => {
                          return (
                            <ul>
                              <tr>{role}</tr>
                            </ul>

                            // <ul>
                            //   <li>{role}</li>
                            // </ul>
                          );
                        })}
                      </th>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </li>
          <li class="list-group-item">
            <b>Equipment: </b>
            (items):
            <table className="table table-hover event-table">
              <tbody>
                {equipment?.map((item) => {
                  return (
                    <tr>
                      <th scope="col">{item?.itemName}</th>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </li>
        </ul>
      </div>

      {/* start table */}

      {buttonsAvailable ? (
        <div className="text-center">
          <i class=" btn fa fa-pencil fa-2x" onClick={props.handleEdit}></i>
          {/* <button className="btn btn-info mr-2" onClick={props.handleEdit}>
            edit
          </button> */}
          <i class="btn fa fa-trash fa-2x" onClick={props.handleDelete}></i>
          {/* <button className="btn btn-danger" onClick={props.handleDelete}>
            delete
          </button> */}
          <i
            class="btn fa fa-share-square fa-2x"
            onClick={props.handleFlip}
          ></i>
        </div>
      ) : (
        ""
      )}
    </div>
  );
};

export default EventCard;
