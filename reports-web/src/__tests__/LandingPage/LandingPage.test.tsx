import React from 'react';
import { cleanup, render } from '@testing-library/react';
import LandingPage from '../../components/LandingPage/LandingPage';

describe('landing page', () => {
  afterEach(cleanup);

  it('renders without crashing', async () => {
    const result = render(<LandingPage />);
    expect(result).not.toBeUndefined();
  });
});
