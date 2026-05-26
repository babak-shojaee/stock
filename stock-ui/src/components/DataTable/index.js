import React from "react";

const DataTable = ({ stocks, updateRow, deleteRow, undoRow, onSortChange }) => (
  <div className="bg-white rounded-xl shadow overflow-hidden">
    <table className="min-w-full divide-y divide-gray-200">
      <thead className="bg-gray-50">
        <tr>
          <th
            className="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider cursor-pointer hover:text-indigo-600 select-none"
            onClick={() => onSortChange("name")}
          >
            Name ↕
          </th>
          <th className="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
            Current Price
          </th>
          <th className="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
            Last Update
          </th>
          <th className="px-6 py-3 text-right text-xs font-semibold text-gray-500 uppercase tracking-wider">
            Actions
          </th>
        </tr>
      </thead>
      <tbody className="divide-y divide-gray-100">
        {stocks.length ? (
          stocks.map(stock => (
            <tr key={stock.id} className={stock.deleted ? "bg-red-50" : "hover:bg-indigo-50 transition-colors"}>
              <td className={`px-6 py-4 text-sm font-medium ${stock.deleted ? "line-through text-gray-400" : "text-gray-800"}`}>
                {stock.name}
              </td>
              <td className={`px-6 py-4 text-sm ${stock.deleted ? "line-through text-gray-400" : "text-gray-600"}`}>
                {stock.currentPrice}
              </td>
              <td className={`px-6 py-4 text-sm ${stock.deleted ? "text-gray-300" : "text-gray-500"}`}>
                {stock.lastUpdate}
              </td>
              <td className="px-6 py-4 text-right">
                {stock.deleted ? (
                  <button
                    className="bg-green-600 hover:bg-green-700 text-white text-sm font-medium px-3 py-1.5 rounded-lg transition-colors"
                    onClick={() => undoRow(stock.id)}
                  >
                    Undo
                  </button>
                ) : (
                  <>
                    <button
                      className="bg-indigo-600 hover:bg-indigo-700 text-white text-sm font-medium px-3 py-1.5 rounded-lg transition-colors mr-2"
                      onClick={() => updateRow(stock)}
                    >
                      Edit
                    </button>
                    <button
                      className="bg-red-600 hover:bg-red-700 text-white text-sm font-medium px-3 py-1.5 rounded-lg transition-colors"
                      onClick={() => deleteRow(stock.id)}
                    >
                      Delete
                    </button>
                  </>
                )}
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="4" className="px-6 py-12 text-center text-gray-400 text-sm">
              No stocks found. Create one to get started.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  </div>
);

export default DataTable;
