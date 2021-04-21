import React from 'react';
import { cleanup, render, fireEvent } from '@testing-library/react';
import NavBar from '../../components/NavBar/NavBar';
import * as Utils from '../../utils/authenticationUtils';

const mockSignOut = jest.fn();

describe('navbar', () => {
  afterEach(cleanup);

  it('navbar check handle click button', () => {
    const mock = jest.spyOn(Utils, 'isTokenActive');
    mock.mockImplementation(() => true);

    const { getByTestId } = render(<NavBar signOut={mockSignOut} />);
    const clicker = getByTestId('handler-click');
    fireEvent.click(clicker);
    expect(mockSignOut).toHaveBeenCalled();
  });
});
