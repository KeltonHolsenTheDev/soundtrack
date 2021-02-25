import React, { useContext, useState } from "react";
import { AuthContext } from "../../auth/auth";

const LocationCard = function (props) {
  const { user } = useContext(AuthContext);

  const buttonsAvailable = user && user.access === "ROLE_ADMINISTRATOR";
  return (
    <div class="card mt-3 text-center">
      <div class="card-body text-center">
        <h5 class="card-title">{props.locationId}</h5>
        <p class="card-text">{props.name}</p>
        <img src={props.image} alt="" className="event-img" />
      </div>
      <div>
        <p className="card-text">{props.address}</p>
      </div>
      {buttonsAvailable ? (
        <div className="text-center">
          <i class=" btn fa fa-pencil fa-2x" onClick={props.handleEdit}></i>
          {/* <button className="btn btn-info mr-2" onClick={props.handleEdit}>
            edit
          </button> */}
          <i class="btn fa fa-trash fa-2x" onClick={props.handleDelete}></i>
          {/* <button className="btn btn-danger" onClick={props.handleDelete}>
            delete
          </button> */}
        </div>
      ) : (
        ""
      )}
    </div>

    //       </div>
    //     </div>
    //   </div>
    // </div>
  );
};
export default LocationCard;
