import React from "react";

// Styles
import "./style.scss";

// Images

import SortIcon from "../../img/sort-icon.png";

const DataTable = props => {
  return (
    <div className="table-wrapper">
      <table className="data-table">
        <thead>
          <tr>

            <th
              onClick={() => {
                props.onSortChange("name");
              }}
            >
              <span className="column-sort">
                Name
                <img src={SortIcon} alt="Name" />
              </span>
            </th>
            <th
              // onClick={() => {
              //   props.onSortChange("currentPrice");
              // }}
            >
              <span className="column-sort">
                Current Price
                <img src={SortIcon} alt="Current Price" />
              </span>
            </th>
            <th
              // onClick={() => {
              //   props.onSortChange("lastUpdate");
              // }}
            >
              <span className="column-none">
                Last Update
                <img src={SortIcon} alt="Last Update" />
              </span>
            </th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {props.stocks.length ? (
            props.stocks.map(stock => (
              <tr key={stock.id}>

                <td>{stock.name}</td>
                <td>{stock.currentPrice}</td>
                <td>{stock.lastUpdate}</td>
                <td className="field-actions">
                  <button
                    className="primary-btn"
                    onClick={() => {
                      props.updateRow(stock);
                    }}
                  >
                    Update
                  </button>

                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5">
                <div className="no-record-message">No Record!</div>
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default DataTable;
