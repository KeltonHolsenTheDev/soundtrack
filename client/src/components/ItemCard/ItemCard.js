import React from "react";
import "./ItemCard.css";

const ItemCard = function (props) {
  return (
    <tr>
      <th scope="row">{props.itemId}</th>
      <td>{props.itemName}</td>
      <td>{props.description}</td>
      <td>{props.brand}</td>
      <td>{props.itemType}</td>
      <td>{props.itemCategory}</td>
      <td>{props.broken ? "Needs repair" : "Good"}</td>
      <td>{props.locationId}</td>
      <td>{props.locationDescription}</td>

      <td>{props.notes}</td>
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

export default ItemCard;
