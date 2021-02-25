import React, { useContext } from "react";
import { AuthContext } from "../../auth/auth";

const UserCard = function (props) {
  const { user } = useContext(AuthContext);
  const buttonsAvailable =
    user && (user.access === "ROLE_ADMINISTRATOR" || user.sub === props.email);
  return (
    // <div class="card">
    //   <div class="card-body">This is some text within a card body.</div>
    // </div>
    <tr>
      <th scope="row">{props.userId}</th>
      <td>{props.firstName}</td>
      <td>{props.lastName}</td>
      <td>{props.phone}</td>
      <td>{props.email}</td>
      <td>{props.role}</td>
      {buttonsAvailable ? (
        <td>
          <i className=" btn fa fa-pencil fa-2x" onClick={props.handleEdit}></i>
          {/* <button className="btn btn-info mr-2" onClick={props.handleEdit}>
            edit
          </button> */}
          <i className="btn fa fa-trash fa-2x" onClick={props.handleDelete}></i>
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
export default UserCard;
