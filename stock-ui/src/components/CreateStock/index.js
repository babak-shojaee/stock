import React, { useState } from "react";

const CreateStock = props => {
  const initialData = { id: null, name: "", currentPrice: " "};
  const [srock, setStock] = useState(initialData);

  const onInputChange = event => {
    const { name, value } = event.target;

    setStock({ ...srock, [name]: value });
  };

  const cancel = event => {
    event.preventDefault();
    props.setActiveModal({ active: false });
  };

  return (
    <form
      onSubmit={event => {
        event.preventDefault();
        if (!srock.name) return;
        props.createStock(srock);
      }}
    >
      <div className="form-group">
        <label>Name</label>
        <input
          type="text"
          name="name"
          value={srock.name}
          onChange={onInputChange}
        />
      </div>
      <div className="form-group">
        <label>Current Price</label>
        <input
          type="text"
          name="currentPrice"
          value={srock.currentPrice}
          onChange={onInputChange}
        />
      </div>

      <div className="form-group form-group--actions">
        <button className="primary-btn">Create</button>
        <button className="cancel-btn" onClick={cancel}>
          Cancel
        </button>
      </div>
    </form>
  );
};

export default CreateStock;
