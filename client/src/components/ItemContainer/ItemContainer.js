import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../../auth/auth";
import "./ItemContainer.css";
import ItemCard from "../ItemCard";
import EditItem from "../EditItem";
// import ItemForm from "../ItemForm";

const ItemContainer = function ({ enableEdit, setEnableEdit }) {
  useAuth();
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
  const [chosenItem, setChosenItem] = useState(null);

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

  const handleEdit = function (event, editedItem) {
    event.preventDefault();
    setChosenItem(editedItem);
    setEnableEdit(true);
  };

  const handleDelete = function (event, deletedItem) {
    event.preventDefault();
    if (!window.confirm("Are you sure?")) {
      return;
    }
    axios
      .delete(`/api/item/${deletedItem.itemId}`)
      .then(function (response) {
        // setItems(items.filter((i) => i.itemId != deletedItem.itemId));
        renderItems();
      })
      .catch(function (error) {
        console.log(error.response);
      });
  };

  return enableEdit ? (
    <EditItem
      chosenItem={chosenItem}
      setEnableEdit={setEnableEdit}
      renderItems={renderItems}
    />
  ) : (
    <table className="table table-hover table-striped text-center">
      <thead>
        <tr>
          <h1 className="ml-3">ITEMS</h1>
        </tr>

        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Description</th>
          <th scope="col">Brand</th>
          <th scope="col">Type</th>
          <th scope="col">Category</th>
          <th scope="col">Condition</th>
          <th scope="col">Location</th>
          <th scope="col">Storage Location Description</th>
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
              description={item.description}
              brand={item.brand}
              itemType={item.itemType}
              itemCategory={item.itemCategory}
              locationName={item?.location?.name}
              locationDescription={item.locationDescription}
              broken={item.broken}
              notes={item.notes}
              key={item.itemId}
              handleEdit={(event) => {
                handleEdit(event, item);
              }}
              handleDelete={(event) => {
                handleDelete(event, item);
              }}
            />
          );
        })}
      </tbody>
    </table>
  );
};

export default ItemContainer;
