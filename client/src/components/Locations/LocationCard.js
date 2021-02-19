import React from "react";

const LocationCard = function (props) {
  return (
    // <div class="card">
    //   <div class="card-body">This is some text within a card body.</div>
    // </div>
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
