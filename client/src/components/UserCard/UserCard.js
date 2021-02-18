import React from "react";

const UserCard = function (props) {
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
export default UserCard;
