import axios from "axios";

const apiURL = process.env.REACT_APP_REQRES_API;

export const getStocks = () => axios.get(`${apiURL}/stocks/`);

export const getCreatedStock = ({ name, currentPrice, lastUpdate }) =>
  axios.post(`${apiURL}/stocks/`, { name, currentPrice, lastUpdate });

export const getUpdatedStock = (id, { name, currentPrice }) =>
  axios.put(`${apiURL}/stocks/${id}`, { id, name, currentPrice });

export const deleteStock = (id) =>
  axios.delete(`${apiURL}/stocks/${id}`);
