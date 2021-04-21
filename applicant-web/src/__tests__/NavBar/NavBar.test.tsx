import React from 'react';
import { act, cleanup, render, screen } from '@testing-library/react';
import { UserManager } from 'oidc-client';
import NavBar from '../../components/NavBar/NavBar';

describe('navbar', () => {
  afterEach(cleanup);

  const userManager = new UserManager({});

  it('navbar stuff', async () => {
    await act(async () => {
      render(<NavBar userManager={userManager} />);
    });

    const strongElement = screen.getByText(
      /Official website of the Department of Homeland Security/i
    );
    expect(strongElement).toBeInTheDocument();
  });

  it('navbar stuff 2', async () => {
    await act(async () => {
      render(<NavBar />);
    });

    const strongElement = screen.queryByText(/View Users/i);
    expect(strongElement).not.toBeInTheDocument();
  });

  it('navbar stuff 3', async () => {
    await act(async () => {
      render(<NavBar />);
    });

    const strongElement = screen.queryByText(/Logout/i);
    expect(strongElement).not.toBeInTheDocument();
  });

  it('navbar stuff 4', async () => {
    await act(async () => {
      render(<NavBar />);
    });

    const strongElement = screen.queryByText(/Locations/i);
    expect(strongElement).not.toBeInTheDocument();
  });
});
