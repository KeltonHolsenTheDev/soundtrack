import React, { useState, useEffect } from "react";
import axios from "axios";

const ItemContainer = function () {
  const [items, setItems] = useState([]);

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
