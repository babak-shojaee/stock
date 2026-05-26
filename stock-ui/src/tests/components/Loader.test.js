import React from 'react';
import { render, screen } from '@testing-library/react';
import Loader from '../../components/Loader';

test('renders a spinner element', () => {
  const { container } = render(<Loader />);
  expect(container.querySelector('.animate-spin')).toBeInTheDocument();
});

test('matches snapshot', () => {
  const { asFragment } = render(<Loader />);
  expect(asFragment()).toMatchSnapshot();
});
