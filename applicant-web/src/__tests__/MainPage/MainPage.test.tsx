import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import MainPage from '../../components/MainPage/MainPage';

describe('main page', () => {
  afterEach(cleanup);

  it('renders Main Page', () => {
    render(<MainPage />);
    const strongElement = screen.getByText(
      /You are accessing a U\.S\. Government \(USG\) Information System \(IS\) that is provided for USG-authorized use only\./i
    );
    expect(strongElement).toBeInTheDocument();
  });
});
