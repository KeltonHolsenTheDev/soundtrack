import React, { useContext, useState } from "react";
import { AuthContext } from "../../auth/auth";
import { useHistory } from "react-router-dom";
import "./UserForm.css";
import { Formik } from "formik";

const UserForm = function ({ defaultUser, submitFcn, formTitle }) {
  const history = useHistory();
  console.log(defaultUser);

  const { user, logoutUser } = useContext(AuthContext);
  const [newUser, setNewUser] = useState(defaultUser);
  const [roleInput, setRoleInput] = useState("");
  const [roles, setRoles] = useState(defaultUser.roles);
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const onChangeHandler = (event) => {
    const updatedUser = { ...newUser };
    updatedUser[event.target.name] = event.target.value;

    setNewUser(updatedUser);
  };

  const handleAddRole = (event) => {
    event.preventDefault();
    const newRoles = [...roles];
    newRoles.push(roleInput);
    setRoles(newRoles);
    setRoleInput("");
  };

  const handleDeleteRole = (event, role) => {
    event.preventDefault();
    const newRoles = [...roles];
    newRoles.splice(newRoles.indexOf(role), 1);
    setRoles(newRoles);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (newUser.password == confirmPassword) {
      newUser.roles = roles;
      submitFcn(newUser, history);
    } else {
      alert("Passwords must match");
    }
  };

  return (
    <div className="container-fluid userform-background">
      <div className="row ">
        <div className="col-3"></div>
        <div className="col-6 userform-card-container d-flex mt-3">
          <div className="card shadow-lg px-4 w-100 rounded-0 userform-card">
            <div className="card-body userform-body">
              <h1 className="card-title mb-3 text-center">
                {`${formTitle} User`}{" "}
              </h1>

              <form onSubmit={handleSubmit}>
                <div className="form-group userform-form-group">
                  <label htmlFor="formGroupExampleInput">First Name</label>
                  <input
                    type="text"
                    name="firstName"
                    className="form-control"
                    id="formGroupExampleInput"
                    placeholder="Jane"
                    onChange={onChangeHandler}
                    value={newUser.firstName}
                    required
                  />
                </div>
                <div className="form-group userform-form-group">
                  <label htmlFor="formGroupExampleInput2">Last Name</label>
                  <input
                    type="text"
                    name="lastName"
                    className="form-control"
                    id="formGroupExampleInput2"
                    placeholder="Doe"
                    onChange={onChangeHandler}
                    value={newUser.lastName}
                    required
                  />
                </div>
                <div className="form-group userform-form-group">
                  <label htmlFor="formGroupExampleInput">Phone</label>
                  <input
                    type="tel"
                    name="phone"
                    pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
                    className="form-control"
                    id="formGroupExampleInput"
                    placeholder="###-###-####"
                    onChange={onChangeHandler}
                    value={newUser.phone}
                    required
                  />
                </div>
                <div className="form-group userform-form-group ">
                  <label htmlFor="inputEmail3">Email</label>
                  <div>
                    <input
                      type="email"
                      name="email"
                      className="form-control"
                      id="inputEmail3"
                      placeholder="jane@email.com"
                      onChange={onChangeHandler}
                      value={newUser.email}
                      required
                    />
                  </div>
                </div>
                <div className="form-group userform-form-group">
                  <label htmlFor="inputPassword">Password</label>
                  <div>
                    <input
                      type="password"
                      name="password"
                      className="form-control"
                      id="inputPassword"
                      placeholder="Password"
                      minlength="16"
                      onChange={onChangeHandler}
                      value={newUser.password}
                      required
                    />
                  </div>
                </div>
                <div className="form-group userform-form-group">
                  <label htmlFor="inputConfirmPassword">Confirm Password</label>
                  <div>
                    <input
                      type="password"
                      name="confirmPassword"
                      className="form-control"
                      id="inputConfirmPassword"
                      placeholder="Confirm password"
                      minlength="16"
                      onChange={(e) => {
                        setConfirmPassword(e.target.value);
                      }}
                      value={confirmPassword}
                      required
                    />
                  </div>
                </div>
                {user && user.access === "ROLE_ADMINISTRATOR" ? (
                  <div>
                    <label htmlFor="inputAccessLevel">Access Level</label>
                    <select
                      name="accessLevel"
                      className="custom-select custom-select-sm mb-2"
                      required
                      value={newUser.accessLevel}
                      onChange={onChangeHandler}
                    >
                      <option value="">Open this select menu</option>
                      <option value="ROLE_ADMINISTRATOR">Administrator</option>
                      <option value="ROLE_USER">User</option>
                    </select>
                  </div>
                ) : (
                  ""
                )}

                <div className="form-group userform-form-group">
                  <label htmlFor="formGroupExampleInput">Roles</label>
                  {roles.map((role) => (
                    <p key={roles.indexOf(role)}>
                      {role}
                      <button
                        type="button"
                        className="close"
                        aria-label="Close"
                        required
                        onClick={(e) => handleDeleteRole(e, role)}
                      >
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </p>
                  ))}
                  <input
                    type="text"
                    className="form-control"
                    id="formGroupExampleInput"
                    placeholder="Technician"
                    onChange={(e) => setRoleInput(e.target.value)}
                    value={roleInput}
                  />
                  <button
                    className="btn btn-info rounded-0 mt-3 mb-5"
                    onClick={(e) => handleAddRole(e)}
                  >
                    Add role
                  </button>
                </div>
                <div className="form-group userform-form-group row">
                  <div className="col-sm-10">
                    <button type="submit" className="btn btn-primary rounded-0">
                      {formTitle}
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div className="col-3"></div>
      </div>
    </div>
  );
};
export default UserForm;
