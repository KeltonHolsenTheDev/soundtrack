import React, { useState, useEffect } from "react";

import axios from "axios";

import "./UserContainer.css";
import UserCard from "../UserCard";

const UserContainer = function () {
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

  useEffect(() => {
    axios.get("/api/user").then(
      (response) => {
        setUsers(response.data);
      },
      (error) => {
        console.log(error);
      }
    );
  }, []);

  const handleEdit = function (event, user) {
    event.preventDefault();
    console.log(`editing user ${user.userId}`);
  };

  const handleDelete = function (event, userId) {
    event.preventDefault();
    console.log(`deleting user ${userId}`);
  };

  return (
    <div className="container">
      <table className="table table-hover table-striped">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">First</th>
            <th scope="col">Last</th>
            <th scope="col">Phone</th>
            <th scope="col">Email</th>
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
                key={user.userId}
                handleEdit={(event) => {
                  handleEdit(event, user);
                }}
                handleDelete={(event) => {
                  handleDelete(event, user.userId);
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
