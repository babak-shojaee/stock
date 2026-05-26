import React from 'react';
import { render, screen } from '@testing-library/react';
import Footer from '../../components/Footer';

test('renders copyright text', () => {
  render(<Footer />);
  expect(screen.getByText(/Stocks App by Babak/i)).toBeInTheDocument();
});

test('matches snapshot', () => {
  const { asFragment } = render(<Footer />);
  expect(asFragment()).toMatchSnapshot();
});
