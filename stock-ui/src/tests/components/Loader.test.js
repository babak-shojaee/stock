import React from 'react'
import { render } from '@testing-library/react'

import Loader from '../../components/Loader/index'

test('Should render Loader correctly', () => {
  const { asFragment } = render(<Loader />)
  expect(asFragment()).toMatchSnapshot()
})