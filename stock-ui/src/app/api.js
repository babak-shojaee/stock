import axios from "axios";

const apiURL = process.env.REACT_APP_REQRES_API;

function getStocks() {
  const response = axios.get(`${apiURL}/stocks/`);


  return response;
}

function getCreatedStock({ name, currentPrice, lastUpdate }) {
  const response = axios.post(`${apiURL}/stocks/`, {
    name,
    currentPrice,
    lastUpdate
  });

  return response;
}

function getUpdatedStock(id, stock) {
  const response = axios.put(`${apiURL}/stocks/${id}`, {
    id: id,
    name: stock.name,
    currentPrice: stock.currentPrice


  });

  return response;
}


export { getStocks, getCreatedStock, getUpdatedStock };
