import React, { useContext } from "react";
import "./ItemCard.css";
import { AuthContext } from "../../auth/auth";

const ItemCard = function (props) {
  const { user } = useContext(AuthContext);
  const buttonsAvailable =
    user && (user.access === "ROLE_ADMINISTRATOR" || user.sub === props.email);
  return (
    <tr>
      <th scope="row">{props.itemId}</th>
      <td>{props.itemName}</td>
      <td>{props.description}</td>
      <td>{props.brand}</td>
      <td>{props.itemType}</td>
      <td>{props.itemCategory}</td>
      <td>{props.broken ? "Needs repair" : "Good"}</td>
      <td>{props.locationName}</td>
      <td>{props.locationDescription}</td>
      <td>{props.notes}</td>
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

export default ItemCard;
