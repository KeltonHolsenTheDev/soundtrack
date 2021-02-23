import React, { useState, useContext } from "react";
import { AuthContext , useAuth} from "../../auth/auth";
import { useHistory } from "react-router-dom";
import UserForm from "../UserForm";

import "./Registration.css";

const Registration = function () {
  useAuth();
  const { user, registerUser } = useContext(AuthContext);
  const history = useHistory();

  if (user.access !== "ROLE_ADMINISTRATOR") {
    history.push("/login");
  }

  const blankUser = {
    id: 0,
    firstName: "",
    lastName: "",
    phone: "",
    email: "",
    accessLevel: "",
    password: "",
    roles: [],
  };

  const addUser = function (newUser, history) {
    registerUser(newUser, history);
  };

  return (
    <UserForm
      defaultUser={blankUser}
      submitFcn={addUser}
      formTitle="Register"
    />
    // <div className="container-fluid register-background">
    //   <div className="row ">
    //     <div className="col-3"></div>
    //     <div className="col-6 register-card-container d-flex mt-3">
    //       <div className="card shadow-lg px-4 w-100 rounded-0 register-card">
    //         <div className="card-body register-body">
    //           <h1 className="card-title mb-3 text-center">Register</h1>
    //           <form onSubmit={handleSubmit}>
    //             <div className="form-group register-form-group">
    //               <label htmlFor="formGroupExampleInput">First Name</label>
    //               <input
    //                 type="text"
    //                 name="firstName"
    //                 className="form-control"
    //                 id="formGroupExampleInput"
    //                 placeholder="Jane"
    //                 onChange={onChangeHandler}
    //                 value={newUser.firstName}
    //                 required
    //               />
    //             </div>
    //             <div className="form-group register-form-group">
    //               <label htmlFor="formGroupExampleInput2">Last Name</label>
    //               <input
    //                 type="text"
    //                 name="lastName"
    //                 className="form-control"
    //                 id="formGroupExampleInput2"
    //                 placeholder="Doe"
    //                 onChange={onChangeHandler}
    //                 value={newUser.lastName}
    //                 required
    //               />
    //             </div>
    //             <div className="form-group register-form-group">
    //               <label htmlFor="formGroupExampleInput">Phone</label>
    //               <input
    //                 type="tel"
    //                 name="phone"
    //                 pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
    //                 className="form-control"
    //                 id="formGroupExampleInput"
    //                 placeholder="###-###-####"
    //                 onChange={onChangeHandler}
    //                 value={newUser.phone}
    //                 required
    //               />
    //             </div>
    //             <div className="form-group register-form-group ">
    //               <label htmlFor="inputEmail3">Email</label>
    //               <div>
    //                 <input
    //                   type="email"
    //                   name="email"
    //                   className="form-control"
    //                   id="inputEmail3"
    //                   placeholder="jane@email.com"
    //                   onChange={onChangeHandler}
    //                   value={newUser.email}
    //                   required
    //                 />
    //               </div>
    //             </div>
    //             <div className="form-group register-form-group">
    //               <label htmlFor="inputPassword">Password</label>
    //               <div>
    //                 <input
    //                   type="password"
    //                   name="password"
    //                   className="form-control"
    //                   id="inputPassword"
    //                   placeholder="Password"
    //                   onChange={onChangeHandler}
    //                   value={newUser.password}
    //                   required
    //                 />
    //               </div>
    //             </div>
    //             <div className="form-group register-form-group">
    //               <label htmlFor="inputConfirmPassword">Confirm Password</label>
    //               <div>
    //                 <input
    //                   type="password"
    //                   name="confirmPassword"
    //                   className="form-control"
    //                   id="inputConfirmPassword"
    //                   placeholder="Confirm password"
    //                   onChange={(e) => {
    //                     setConfirmPassword(e.target.value);
    //                   }}
    //                   value={confirmPassword}
    //                   required
    //                 />
    //               </div>
    //             </div>
    //             <label htmlFor="inputAccessLevel">Access Level</label>
    //             <select
    //               name="accessLevel"
    //               className="custom-select custom-select-sm mb-2"
    //               required
    //               value={newUser.accessLevel}
    //               onChange={onChangeHandler}
    //             >
    //               <option value="">Open this select menu</option>
    //               <option value="ROLE_ADMINISTRATOR">Administrator</option>
    //               <option value="ROLE_USER">User</option>
    //             </select>

    //             <div className="form-group register-form-group row">
    //               <div className="col-sm-10">
    //                 <button type="submit" className="btn btn-primary rounded-0">
    //                   Register
    //                 </button>
    //               </div>
    //             </div>
    //           </form>
    //         </div>
    //       </div>
    //     </div>
    //     <div className="col-3"></div>
    //   </div>
    // </div>
  );
};
export default Registration;
