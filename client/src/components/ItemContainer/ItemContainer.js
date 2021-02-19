import React, { useState, useEffect } from "react";
import axios from "axios";
import "./ItemContainer.css";
import ItemCard from "../ItemCard";

const ItemContainer = function () {
  const testItems = [
    {
      itemId: 1,
      itemName: "test item",
      description: "test item description",
      brand: "test brand",
      itemType: "test item type",
      itemCategory: "OTHER",
      locationId: 2,
      location: {
        locationId: 2,
        address: "777 test some place",
        name: "test house",
      },
      locationDescription: "red bin on shelf 2",
      notes: "f;aldsfj;dslkf",
      broken: false,
    },
  ];

  const [items, setItems] = useState([testItems]);

  const renderItems = function () {
    axios
      .get("/api/item")
      .then(function (response) {
        console.log(response.data);
        setItems(response.data);
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  useEffect(() => {
    renderItems();
  }, []);

  return (
    <div className="container">
      <table className="table table-hover table-striped">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Brand</th>
            <th scope="col">Type</th>
            <th scope="col">Category</th>
            <th scope="col">Broken</th>
            <th scope="col">Notes</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {items.map((item) => {
            return (
              <ItemCard
                itemId={item.itemId}
                itemName={item.itemName}
                descriptionName={item.description}
                brand={item.brand}
                itemType={item.itemType}
                itemCategory={item.itemCategory}
                isBroken={item.broken}
                notes={item.notes}
                key={item.itemId}
                // handleEdit={(event) => {
                //   handleEdit(event, user);
                // }}
                // handleDelete={(event) => {
                //   handleDelete(event, user);
                // }}
              />
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default ItemContainer;
