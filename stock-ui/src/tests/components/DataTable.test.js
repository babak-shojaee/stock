import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import DataTable from '../../components/DataTable';

const stocks = [
  { id: 1, name: 'AAPL', currentPrice: 150, lastUpdate: '2024-01-01', deleted: false },
  { id: 2, name: 'GOOG', currentPrice: 200, lastUpdate: '2024-01-02', deleted: true },
];

const noop = jest.fn();

test('renders stock rows', () => {
  render(<DataTable stocks={stocks} updateRow={noop} deleteRow={noop} undoRow={noop} onSortChange={noop} />);
  expect(screen.getByText('AAPL')).toBeInTheDocument();
  expect(screen.getByText('GOOG')).toBeInTheDocument();
});

test('shows empty message when no stocks', () => {
  render(<DataTable stocks={[]} updateRow={noop} deleteRow={noop} undoRow={noop} onSortChange={noop} />);
  expect(screen.getByText(/No stocks found/i)).toBeInTheDocument();
});

test('shows Edit/Delete for active stock', () => {
  render(<DataTable stocks={[stocks[0]]} updateRow={noop} deleteRow={noop} undoRow={noop} onSortChange={noop} />);
  expect(screen.getByRole('button', { name: /edit/i })).toBeInTheDocument();
  expect(screen.getByRole('button', { name: /delete/i })).toBeInTheDocument();
});

test('shows Undo for deleted stock', () => {
  render(<DataTable stocks={[stocks[1]]} updateRow={noop} deleteRow={noop} undoRow={noop} onSortChange={noop} />);
  expect(screen.getByRole('button', { name: /undo/i })).toBeInTheDocument();
});

test('calls updateRow when Edit clicked', () => {
  const updateRow = jest.fn();
  render(<DataTable stocks={[stocks[0]]} updateRow={updateRow} deleteRow={noop} undoRow={noop} onSortChange={noop} />);
  fireEvent.click(screen.getByRole('button', { name: /edit/i }));
  expect(updateRow).toHaveBeenCalledWith(stocks[0]);
});

test('calls deleteRow when Delete clicked', () => {
  const deleteRow = jest.fn();
  render(<DataTable stocks={[stocks[0]]} updateRow={noop} deleteRow={deleteRow} undoRow={noop} onSortChange={noop} />);
  fireEvent.click(screen.getByRole('button', { name: /delete/i }));
  expect(deleteRow).toHaveBeenCalledWith(1);
});

test('calls undoRow when Undo clicked', () => {
  const undoRow = jest.fn();
  render(<DataTable stocks={[stocks[1]]} updateRow={noop} deleteRow={noop} undoRow={undoRow} onSortChange={noop} />);
  fireEvent.click(screen.getByRole('button', { name: /undo/i }));
  expect(undoRow).toHaveBeenCalledWith(2);
});

test('calls onSortChange when Name header clicked', () => {
  const onSortChange = jest.fn();
  render(<DataTable stocks={stocks} updateRow={noop} deleteRow={noop} undoRow={noop} onSortChange={onSortChange} />);
  fireEvent.click(screen.getByText(/Name/i));
  expect(onSortChange).toHaveBeenCalledWith('name');
});
