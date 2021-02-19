import React, { useState, useEffect } from "react";
import axios from "axios";

const ItemContainer = function () {
  const testItems = [
    {
      itemName: "test item",
      description: "test item description",
      brand: "test brand",
      itemType: "test item type",
      itemCategory: "OTHER",
      location: {
        locationId: 2,
        name: "test house",
        address: "777 test some place",
      },
      locationDescription: "red bin on shelf 2",
      isBroken: true,
      notes: "f;aldsfj;dslkf",
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

  return <></>;
};

export default ItemContainer;
