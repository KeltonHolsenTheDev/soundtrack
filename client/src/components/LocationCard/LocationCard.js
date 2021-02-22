import React from "react";
import "./LocationCard.css";

const LocationCard = function (props) {
  return (
    <tr className="card-list">
      <th>{props.locationId}</th>
      <td>{props.name}</td>
      <td>{props.address}</td>
      <td>
        <button className="btn btn-info mr-2" onClick={props.handleEdit}>
          edit
        </button>
        <button className="btn btn-danger" onClick={props.handleDelete}>
          delete
        </button>
      </td>
    </tr>
  );
};
export default LocationCard;
