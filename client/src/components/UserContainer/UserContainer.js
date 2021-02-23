import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../../auth/auth";
import { useHistory } from "react-router-dom";

import "./UserContainer.css";
import UserCard from "../UserCard";
import EditUser from "../EditUser";

const UserContainer = function ({ enableEdit, setEnableEdit }) {
  // const users = [
  //   {
  //     firstName: "Mark",
  //     lastName: "Otto",
  //     phone: "111-111-1111",
  //     email: "@mdo",
  //   },
  //   {
  //     firstName: "Jacob",
  //     lastName: "Thornton",
  //     phone: "222-222-2222",
  //     email: "@fat",
  //   },
  //   {
  //     firstName: "Jenny",
  //     lastName: "Johnson",
  //     phone: "333-333-3333",
  //     email: "@twitter",
  //   },
  // ];

  const [users, setUsers] = useState([]);
  const [chosenUser, setChosenUser] = useState(null);
  const { user, logoutUser } = useContext(AuthContext);
  const history = useHistory();

  if (!user) {
    history.push("/login");
  }

  const renderUsers = function () {
    axios.get("/api/user").then(
      (response) => {
        setUsers(response.data);
      },
      (error) => {
        console.log(error.response);
      }
    );
  };

  useEffect(() => {
    renderUsers();
  }, []);

  const handleEdit = function (event, editedUser) {
    event.preventDefault();
    setChosenUser(editedUser);
    setEnableEdit(true);
  };

  const handleDelete = function (event, deletedUser) {
    event.preventDefault();
    if (!window.confirm("CONFIRM ACCOUNT DELETION.")) {
      return;
    }
    axios
      .delete(`/api/user/${deletedUser.userId}`)
      .then(function (response) {
        if (user.sub === deletedUser.email) {
          logoutUser();
          history.push("/login");
        } else {
          renderUsers();
        }
      })
      .catch(function (error) {
        console.log(error.response.data);
      });
  };

  return enableEdit ? (
    <EditUser
      chosenUser={chosenUser}
      setEnableEdit={setEnableEdit}
      renderUsers={renderUsers}
    />
  ) : (
    <div className="container">
      <table className="table table-hover table-striped">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">First</th>
            <th scope="col">Last</th>
            <th scope="col">Phone</th>
            <th scope="col">Email</th>
            <th scope="col">Role</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => {
            return (
              <UserCard
                userId={user.userId}
                firstName={user.firstName}
                lastName={user.lastName}
                phone={user.phone}
                email={user.email}
                role={user.accessLevel}
                key={user.userId}
                handleEdit={(event) => {
                  handleEdit(event, user);
                }}
                handleDelete={(event) => {
                  handleDelete(event, user);
                }}
              />
            );
          })}
        </tbody>
      </table>
    </div>
  );
};
export default UserContainer;
