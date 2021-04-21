import React from 'react';
import { cleanup, render, fireEvent } from '@testing-library/react';
import NavBar from '../../components/NavBar/NavBar';
import * as Utils from '../../utils/authenticationUtils';

const mockSignOut = jest.fn();

describe('navbar', () => {
  afterEach(cleanup);

  it('navbar logout opens confirm dialog and then logs out when confirmed', () => {
    const mock = jest.spyOn(Utils, 'isTokenActive');
    mock.mockImplementation(() => true);

    const { getByTestId } = render(<NavBar signOut={mockSignOut} />);
    fireEvent.click(getByTestId('handler-click'));
    fireEvent.click(getByTestId('logout-dialog-confirm'));
    expect(mockSignOut).toHaveBeenCalled();
  });

  it('navbar logout opens confirm dialog and then does not log out when no clicked', () => {
    const mock = jest.spyOn(Utils, 'isTokenActive');
    mock.mockImplementation(() => true);

    const { getByTestId } = render(<NavBar signOut={mockSignOut} />);
    fireEvent.click(getByTestId('handler-click'));
    fireEvent.click(getByTestId('logout-dialog-nope'));
    expect(mockSignOut).not.toHaveBeenCalled();
  });
});
