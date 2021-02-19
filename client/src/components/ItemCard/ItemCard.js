import React from "react";
import "./ItemCard";

const ItemCard = function (props) {
  return (
    <tr>
      <th scope="row">{props.itemId}</th>
      <td>{props.firstName}</td>
      <td>{props.lastName}</td>
      <td>{props.phone}</td>
      <td>{props.email}</td>
      <td>{props.role}</td>
    </tr>
  );
};
