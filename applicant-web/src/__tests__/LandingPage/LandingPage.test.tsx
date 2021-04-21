import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { User } from 'oidc-client';
import LandingPage from '../../components/LandingPage/LandingPage';
import * as UserUtils from '../../utils/userService';
import * as FormUtils from '../../utils/formService';
import { Person } from '../../utils/usersUtils';
import {
  I131FormValues,
  IN_PROGRESS,
  SUBMITTED,
  formTestData as form
} from '../../utils/formUtils';

describe('landing page', () => {
  const userInfoReturn: Person = {
    usernameId: '1',
    name: 'FirstName LastName',
    email: 'email@email.com'
  };

  const user: User = {
    id_token: 'string',
    access_token: 'string',
    token_type: 'string',
    scope: 'string',
    scopes: ['string'],
    expires_at: 123456789,
    expires_in: 234567890,
    expired: false,
    state: 'string',
    toStorageString: () => 'string',

    profile: {
      'cognito:username': 'acb123',
      iss: 'string',
      aud: 'string',
      sub: 'string',
      exp: 0,
      iat: 1
    }
  };

  const forms: I131FormValues[] = [
    {
      ...form,
      formNumber: 'abc123',
      submissionDate: '02-17-2021',
      submissionStatus: IN_PROGRESS
    },
    {
      ...form,
      formNumber: 'xyz987',
      submissionDate: '03-03-2021',
      submissionStatus: SUBMITTED
    }
  ];

  beforeEach(() => {
    jest.spyOn(UserUtils, 'getUserInfo').mockResolvedValue(userInfoReturn);
    jest.spyOn(FormUtils, 'getFormsByUserId').mockResolvedValue(forms);
  });

  afterEach(cleanup);

  it('welcomes user', async () => {
    await act(async () => {
      render(<LandingPage user={user} />);
    });
    const welcome = screen.getByText(`Welcome ${userInfoReturn.name}`);

    expect(welcome).toBeInTheDocument();
  });

  it('shows submitted forms table', async () => {
    await act(async () => {
      render(<LandingPage />);
    });
    const text = screen.getByText('Submitted Applications');

    expect(text).toBeInTheDocument();
  });

  it('shows in progress forms table', async () => {
    await act(async () => {
      render(<LandingPage />);
    });
    const inProgressText = screen.getByText('Applications In Progress');

    expect(inProgressText).toBeInTheDocument();
  });
});
