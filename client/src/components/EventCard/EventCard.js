import React, { useContext } from "react";
import "./EventCard.css";
import { AuthContext } from "../../auth/auth";

const EventCard = function (props) {
  const { user } = useContext(AuthContext);
  const buttonsAvailable =
    user &&
    (user.access === "ROLE_ADMINISTRATOR" || user.sub === props.owner.email);

  const owner = props.owner;
  const location = props.location;
  const staffAndRoles = props.staffAndRoles;
  const equipment = props.equipment;
  return (
    <tr>
      <th scope="row">{props.eventId}</th>
      <td>{props.eventName}</td>
      <td>{props.startDate}</td>
      <td>{props.endDate}</td>
      <td>{owner?.lastName}</td>
      <td>{location?.name}</td>
      <td>
        <table className="table table-hover table-dark">
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
                  <th scope="col">{userRole?.roles?.map ((role) => {
                    return (
                      <ul>
                        <li>{role}</li>
                      </ul>
                    );
                  }

                  )}</th>
                </tr> 
              );        
            })}
          </tbody>
        </table>
      </td>
      <td>
        <table className="table table-hover table-dark">
          <thead>
            <tr>
              <th scope="col">Items:</th>
              <th scope="col"></th>
            </tr>
          </thead>
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
      </td>
      {buttonsAvailable ? (
        <td>
          <i class=" btn fa fa-pencil fa-2x" onClick={props.handleEdit}></i>
          {/* <button className="btn btn-info mr-2" onClick={props.handleEdit}>
            edit
          </button> */}
          <i class="btn fa fa-trash fa-2x" onClick={props.handleDelete}></i>
          {/* <button className="btn btn-danger" onClick={props.handleDelete}>
            delete
          </button> */}
        </td>
      ) : (
        ""
      )}
    </tr>
  );
};

export default EventCard;
