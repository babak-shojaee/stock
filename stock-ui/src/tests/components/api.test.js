import axios from 'axios';
import { getStocks, getCreatedStock, getUpdatedStock, deleteStock, restoreStock } from '../../app/api';

jest.mock('axios');

const API = process.env.REACT_APP_REQRES_API;

test('getStocks calls GET /stocks/', () => {
  axios.get.mockResolvedValue({ data: [] });
  getStocks();
  expect(axios.get).toHaveBeenCalledWith(`${API}/stocks/`);
});

test('getCreatedStock calls POST /stocks/', () => {
  axios.post.mockResolvedValue({ data: {} });
  getCreatedStock({ name: 'AAPL', currentPrice: 150, lastUpdate: null });
  expect(axios.post).toHaveBeenCalledWith(`${API}/stocks/`, { name: 'AAPL', currentPrice: 150, lastUpdate: null });
});

test('getUpdatedStock calls PUT /stocks/:id', () => {
  axios.put.mockResolvedValue({ data: {} });
  getUpdatedStock(1, { name: 'AAPL', currentPrice: 160 });
  expect(axios.put).toHaveBeenCalledWith(`${API}/stocks/1`, { id: 1, name: 'AAPL', currentPrice: 160 });
});

test('deleteStock calls DELETE /stocks/:id', () => {
  axios.delete.mockResolvedValue({});
  deleteStock(1);
  expect(axios.delete).toHaveBeenCalledWith(`${API}/stocks/1`);
});

test('restoreStock calls PATCH /stocks/:id/restore', () => {
  axios.patch.mockResolvedValue({});
  restoreStock(1);
  expect(axios.patch).toHaveBeenCalledWith(`${API}/stocks/1/restore`);
});
