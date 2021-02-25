import React, { useContext } from "react";
import { AuthContext } from "../../auth/auth";

const LocationCard = function (props) {
  const { user } = useContext(AuthContext);

  const buttonsAvailable = user && user.access === "ROLE_ADMINISTRATOR";
  return (
    <div className="card mt-3 text-center">
      <div className="card-body text-center">
        <h5 className="card-title">{props.locationId}</h5>
        <p className="card-text">{props.name}</p>
        <img src={props.image} alt="" className="event-img" />
      </div>
      <div>
        <p className="card-text">{props.address}</p>
      </div>
      {buttonsAvailable ? (
        <div className="text-center">
          <i className=" btn fa fa-pencil fa-2x" onClick={props.handleEdit}></i>
          {/* <button className="btn btn-info mr-2" onClick={props.handleEdit}>
            edit
          </button> */}
          <i className="btn fa fa-trash fa-2x" onClick={props.handleDelete}></i>
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
