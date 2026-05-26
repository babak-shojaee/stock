import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import UpdateStock from '../../components/UpdateStock';

const currentStock = { id: 1, name: 'AAPL', currentPrice: '150', lastUpdate: '' };
const setActiveModal = jest.fn();

test('pre-fills inputs with currentStock values', () => {
  render(<UpdateStock currentStock={currentStock} updateStock={jest.fn()} setActiveModal={setActiveModal} />);
  expect(screen.getByLabelText(/name/i)).toHaveValue('AAPL');
  expect(screen.getByLabelText(/current price/i)).toHaveValue('150');
});

test('calls setActiveModal on cancel', () => {
  render(<UpdateStock currentStock={currentStock} updateStock={jest.fn()} setActiveModal={setActiveModal} />);
  fireEvent.click(screen.getByRole('button', { name: /cancel/i }));
  expect(setActiveModal).toHaveBeenCalledWith({ active: false });
});

test('calls updateStock with updated values on submit', async () => {
  const updateStock = jest.fn().mockResolvedValue();
  render(<UpdateStock currentStock={currentStock} updateStock={updateStock} setActiveModal={setActiveModal} />);
  fireEvent.change(screen.getByLabelText(/name/i), { target: { name: 'name', value: 'TSLA' } });
  fireEvent.click(screen.getByRole('button', { name: /update/i }));
  await waitFor(() => expect(updateStock).toHaveBeenCalledWith(1, expect.objectContaining({ name: 'TSLA' })));
});

test('shows error when updateStock rejects', async () => {
  const updateStock = jest.fn().mockRejectedValue(new Error('Duplicate name'));
  render(<UpdateStock currentStock={currentStock} updateStock={updateStock} setActiveModal={setActiveModal} />);
  fireEvent.click(screen.getByRole('button', { name: /update/i }));
  await waitFor(() => expect(screen.getByText('Duplicate name')).toBeInTheDocument());
});
