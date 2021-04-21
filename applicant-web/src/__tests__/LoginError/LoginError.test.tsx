import React from 'react';
import { cleanup, render, screen, fireEvent } from '@testing-library/react';
import LoginError from '../../components/LoginError/LoginError';

const mockSignOut = jest.fn();

describe('login page', () => {
  afterEach(cleanup);

  it('renders Team Steampunk name', () => {
    render(<LoginError signOut={mockSignOut} />);
    const strongElement = screen.getByText(
      /Please log in as a user with the Applicant role to use this application./i
    );
    expect(strongElement).toBeInTheDocument();
  });

  it('handles logout', () => {
    render(<LoginError signOut={mockSignOut} />);
    const clicker = screen.getByTestId('handler-click');
    fireEvent.click(clicker);
    expect(mockSignOut).toHaveBeenCalled();
  });
});
