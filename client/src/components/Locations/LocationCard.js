import React from "react";
import "./Location.css";

const LocationCard = function (props) {
  return (
    <tr>
      <th scope="row">{props.locationId}</th>
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
