import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import CreateStock from '../../components/CreateStock';

const setActiveModal = jest.fn();

test('renders name and price inputs', () => {
  render(<CreateStock createStock={jest.fn()} setActiveModal={setActiveModal} />);
  expect(screen.getByLabelText(/name/i)).toBeInTheDocument();
  expect(screen.getByLabelText(/current price/i)).toBeInTheDocument();
});

test('calls setActiveModal on cancel', () => {
  render(<CreateStock createStock={jest.fn()} setActiveModal={setActiveModal} />);
  fireEvent.click(screen.getByRole('button', { name: /cancel/i }));
  expect(setActiveModal).toHaveBeenCalledWith({ active: false });
});

test('calls createStock with form values on submit', async () => {
  const createStock = jest.fn().mockResolvedValue();
  render(<CreateStock createStock={createStock} setActiveModal={setActiveModal} />);
  fireEvent.change(screen.getByLabelText(/name/i), { target: { name: 'name', value: 'AAPL' } });
  fireEvent.change(screen.getByLabelText(/current price/i), { target: { name: 'currentPrice', value: '150' } });
  fireEvent.click(screen.getByRole('button', { name: /create/i }));
  await waitFor(() => expect(createStock).toHaveBeenCalledWith({ name: 'AAPL', currentPrice: '150' }));
});

test('shows error message when createStock rejects', async () => {
  const createStock = jest.fn().mockRejectedValue(new Error('Name already exists'));
  render(<CreateStock createStock={createStock} setActiveModal={setActiveModal} />);
  fireEvent.change(screen.getByLabelText(/name/i), { target: { name: 'name', value: 'DUP' } });
  fireEvent.change(screen.getByLabelText(/current price/i), { target: { name: 'currentPrice', value: '10' } });
  fireEvent.click(screen.getByRole('button', { name: /create/i }));
  await waitFor(() => expect(screen.getByText('Name already exists')).toBeInTheDocument());
});

test('does not submit when fields are empty', async () => {
  const createStock = jest.fn();
  render(<CreateStock createStock={createStock} setActiveModal={setActiveModal} />);
  fireEvent.click(screen.getByRole('button', { name: /create/i }));
  await waitFor(() => expect(createStock).not.toHaveBeenCalled());
});
