import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import ErrorPage from '../../components/ErrorPage/ErrorPage';

describe('login page', () => {
  afterEach(cleanup);

  it('renders Team Steampunk name', () => {
    render(<ErrorPage />);
    const strongElement = screen.getByText(/This Page Is Under Construction/i);
    expect(strongElement).toBeInTheDocument();
  });
});
