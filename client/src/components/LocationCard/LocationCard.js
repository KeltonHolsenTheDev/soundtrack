import React, { useContext } from "react";
import { AuthContext } from "../../auth/auth";

const LocationCard = function (props) {
  const { user } = useContext(AuthContext);
  const buttonsAvailable =
    user &&
    (user.access === "ROLE_ADMINISTRATOR" || user.sub === props.owner.email);
  return (
    <tr className="card-list">
      <th>{props.locationId}</th>
      <td>{props.name}</td>
      <td>{props.address}</td>
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
export default LocationCard;
