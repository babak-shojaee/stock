import React from 'react'
import { render } from '@testing-library/react'

import Header from '../../components/Header/index'

test('Should render Header correctly', () => {
  const { asFragment } = render(<Header />)
  expect(asFragment()).toMatchSnapshot()
})