import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import NavBar from '../../components/NavBar/NavBar';
import * as Utils from '../../utils/authenticationUtils';

describe('navbar', () => {
  afterEach(cleanup);

  it('navbar check is active logout button', () => {
    const mock = jest.spyOn(Utils, 'isTokenActive');
    mock.mockImplementation(() => true);
    render(<NavBar />);
    const strongElement = screen.queryByText(/Logout/i);
    expect(strongElement).toBeInTheDocument();
  });
  it('navbar check is admin and active Admin button', () => {
    const mock = jest.spyOn(Utils, 'isTokenActive');
    mock.mockImplementation(() => true);
    const mockAdmin = jest.spyOn(Utils, 'isTokenAdmin');
    mockAdmin.mockImplementation(() => true);
    render(<NavBar />);
    const strongElement = screen.queryByText(/Admin/i);
    expect(strongElement).toBeInTheDocument();
  });
});
