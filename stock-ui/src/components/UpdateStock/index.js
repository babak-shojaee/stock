import React, { useState, useEffect } from "react";

const UpdateStock = props => {
  const [stock, setStock] = useState(props.currentStock);

  const onInputChange = event => {
    const { name, value } = event.target;

    setStock({ ...stock, [name]: value });
  };

  const cancel = event => {
    event.preventDefault();
    props.setActiveModal({ active: false });
  };

  useEffect(() => {
    setStock(props.currentStock);
  }, [props]);

  return (
    <form
      onSubmit={event => {
        event.preventDefault();
        props.updateStock(stock.id, stock);
      }}
    >
      <div className="form-group">
        <label>Name</label>
        <input
          type="text"
          name="name"
          value={stock.name}
          onChange={onInputChange}
        />
      </div>
      <div className="form-group">
        <label>Current Price</label>
        <input
          type="text"
          name="currentPrice"
          value={stock.currentPrice}
          onChange={onInputChange}
        />
      </div>

      <div className="form-group form-group--actions">
        <button className="primary-btn">Update</button>
        <button className="cancel-btn" onClick={cancel}>
          Cancel
        </button>
      </div>
    </form>
  );
};

export default UpdateStock;
