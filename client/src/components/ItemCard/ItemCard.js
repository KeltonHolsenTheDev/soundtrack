import React from "react";
import "./ItemCard.css";

const ItemCard = function (props) {
  return (
    <tr>
      <th scope="row">{props.itemId}</th>
      <td>{props.itemName}</td>
      <td>{props.descriptionName}</td>
      <td>{props.brand}</td>
      <td>{props.itemType}</td>
      <td>{props.itemCategory}</td>
      <td>{`${props.isBroken}`}</td>
      <td>{props.notes}</td>
    </tr>
  );
};

export default ItemCard;
