import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Modal from '../../components/Modal';

const activeModal = { name: 'Test Modal', active: true };

test('renders modal title and children', () => {
  render(
    <Modal activeModal={activeModal} onClose={() => {}}>
      <p>Modal content</p>
    </Modal>
  );
  expect(screen.getByText('Test Modal')).toBeInTheDocument();
  expect(screen.getByText('Modal content')).toBeInTheDocument();
});

test('calls onClose when close button clicked', () => {
  const onClose = jest.fn();
  render(<Modal activeModal={activeModal} onClose={onClose}><span /></Modal>);
  fireEvent.click(screen.getByLabelText('Close'));
  expect(onClose).toHaveBeenCalledTimes(1);
});

test('calls onClose when backdrop clicked', () => {
  const onClose = jest.fn();
  const { container } = render(
    <Modal activeModal={activeModal} onClose={onClose}><span /></Modal>
  );
  fireEvent.click(container.firstChild); // backdrop div
  expect(onClose).toHaveBeenCalledTimes(1);
});

test('does not call onClose when inner content clicked', () => {
  const onClose = jest.fn();
  render(<Modal activeModal={activeModal} onClose={onClose}><p>content</p></Modal>);
  fireEvent.click(screen.getByText('content'));
  expect(onClose).not.toHaveBeenCalled();
});
