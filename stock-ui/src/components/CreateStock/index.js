import React, { useState } from "react";

const INITIAL = { name: "", currentPrice: "" };

const CreateStock = ({ createStock, setActiveModal }) => {
  const [stock, setStock] = useState(INITIAL);

  const onChange = ({ target: { name, value } }) =>
    setStock(prev => ({ ...prev, [name]: value }));

  const onCancel = e => {
    e.preventDefault();
    setActiveModal({ active: false });
  };

  const onSubmit = e => {
    e.preventDefault();
    if (!stock.name.trim() || !stock.currentPrice.trim()) return;
    createStock(stock);
  };

  return (
    <form onSubmit={onSubmit} className="space-y-4">
      <div>
        <label htmlFor="create-name" className="block text-sm font-medium text-gray-700 mb-1">
          Name
        </label>
        <input
          id="create-name"
          type="text"
          name="name"
          value={stock.name}
          onChange={onChange}
          required
          className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>
      <div>
        <label htmlFor="create-price" className="block text-sm font-medium text-gray-700 mb-1">
          Current Price
        </label>
        <input
          id="create-price"
          type="text"
          name="currentPrice"
          value={stock.currentPrice}
          onChange={onChange}
          required
          className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>
      <div className="flex justify-end gap-2 pt-2">
        <button
          type="button"
          onClick={onCancel}
          className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 hover:bg-gray-200 rounded-lg transition-colors"
        >
          Cancel
        </button>
        <button
          type="submit"
          className="px-4 py-2 text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg transition-colors"
        >
          Create
        </button>
      </div>
    </form>
  );
};

export default CreateStock;
