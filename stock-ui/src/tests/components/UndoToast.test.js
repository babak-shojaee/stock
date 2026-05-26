import React from 'react';
import { render, screen, fireEvent, act } from '@testing-library/react';
import UndoToast from '../../components/UndoToast';

beforeEach(() => jest.useFakeTimers());
afterEach(() => jest.useRealTimers());

test('renders message and Undo button', () => {
  render(<UndoToast message="Stock deleted" onUndo={jest.fn()} onDismiss={jest.fn()} />);
  expect(screen.getByText('Stock deleted')).toBeInTheDocument();
  expect(screen.getByRole('button', { name: /undo/i })).toBeInTheDocument();
});

test('calls onUndo when Undo clicked', () => {
  const onUndo = jest.fn();
  render(<UndoToast message="msg" onUndo={onUndo} onDismiss={jest.fn()} />);
  fireEvent.click(screen.getByRole('button', { name: /undo/i }));
  expect(onUndo).toHaveBeenCalledTimes(1);
});

test('calls onDismiss after 5 seconds', () => {
  const onDismiss = jest.fn();
  render(<UndoToast message="msg" onUndo={jest.fn()} onDismiss={onDismiss} />);
  act(() => jest.advanceTimersByTime(5000));
  expect(onDismiss).toHaveBeenCalledTimes(1);
});
