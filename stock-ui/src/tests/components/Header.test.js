import React from 'react';
import { render, screen } from '@testing-library/react';
import Header from '../../components/Header';

test('renders the Stocks link', () => {
  render(<Header />);
  expect(screen.getByRole('link', { name: /stocks/i })).toHaveAttribute('href', '/');
});

test('matches snapshot', () => {
  const { asFragment } = render(<Header />);
  expect(asFragment()).toMatchSnapshot();
});
