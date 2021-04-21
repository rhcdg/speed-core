import React, { ReactElement } from 'react';
import ReactGA from 'react-ga';
import {
  cleanup,
  fireEvent,
  render,
  RenderResult,
  screen,
  waitFor
} from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { act } from 'react-dom/test-utils';
import { UserData } from 'react-oidc';
import { User } from 'oidc-client';
import {
  AppRouter,
  getAccessToken,
  ProtectedRoute,
  userManager
} from '../../components/AppRouter/AppRouter';
import * as AuthUtils from '../../utils/authenticationUtils';
import * as UserUtils from '../../utils/userService';
import * as FormUtils from '../../utils/formService';
import {
  APP_URL,
  FORM_PAGE_URL,
  INSTRUCTIONS_URL,
  SELECT_CATEGORY_URL
} from '../../utils/routeUrls';
import { Person } from '../../utils/usersUtils';

const USER_PROFILE: User = {
  id_token: '',
  access_token: '',
  token_type: '',
  scope: '',
  profile: {
    iss: '',
    aud: '',
    exp: 99999999999,
    sub: '',
    iat: 99999999999,
    'cognito:groups': [AuthUtils.roleApplicant]
  },
  expires_at: 99999999999,
  state: '',
  expires_in: 99999999999,
  expired: false,
  scopes: [],
  toStorageString: (): string => ''
};

const PERSON: Person = {
  name: 'FirstName LastName',
  usernameId: 'abc123',
  email: 'email@email.com'
};

const ADMIN_PROFILE: User = {
  ...USER_PROFILE,
  profile: { ...USER_PROFILE.profile, 'cognito:groups': [AuthUtils.roleAdmin] },
  toStorageString: () => ''
};

const renderWithRouter = (ui: ReactElement, { route = '/' } = {}) => {
  window.history.pushState({}, 'test page', route);

  return render(ui, { wrapper: BrowserRouter });
};

Object.defineProperty(window.self, 'crypto', {
  value: {
    getRandomValues: () => crypto.getRandomValues(null)
  }
});

// eslint-disable-next-line @typescript-eslint/no-empty-function
const signOut = async () => {};

describe('application router', () => {
  afterEach(cleanup);

  it('renders router', async () => {
    jest.spyOn(ReactGA, 'initialize').mockReturnValueOnce(undefined);
    jest.spyOn(ReactGA, 'pageview').mockReturnValueOnce(undefined);
    jest.spyOn(ReactGA, 'set').mockReturnValueOnce(undefined);

    await act(async () => {
      renderWithRouter(<AppRouter />, { route: '/' });
    });

    const navbarText = screen.getByText(
      /Official website of the Department of Homeland Security/i
    );
    expect(navbarText).toBeInTheDocument();
  });

  it('authenticated actions can be performed', async () => {
    jest
      .spyOn(userManager, 'getUser')
      .mockImplementation(async () => USER_PROFILE);
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    jest.spyOn(userManager, 'removeUser').mockImplementation(async () => {});
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    jest
      .spyOn(userManager, 'signoutRedirect')
      // eslint-disable-next-line @typescript-eslint/no-empty-function
      .mockImplementation(async () => {});

    expect(getAccessToken()).not.toBeUndefined();

    let renderResult: RenderResult | undefined;

    await act(async () => {
      renderResult = renderWithRouter(
        <UserData.Provider
          value={{
            user: USER_PROFILE,
            signOut,
            userManager
          }}
        >
          <AppRouter />
        </UserData.Provider>,
        { route: '/' }
      );
    });

    if (renderResult) {
      fireEvent.click(renderResult.getByTestId('handler-click'));
    }

    expect(userManager.removeUser).toHaveBeenCalledTimes(1);
    expect(userManager.signoutRedirect).toHaveBeenCalledTimes(1);
    expect(userManager.getUser).toHaveBeenCalledTimes(3);
  });

  it('renders authenticated home route', async () => {
    jest
      .spyOn(userManager, 'getUser')
      .mockImplementation(async () => USER_PROFILE);

    jest.spyOn(UserUtils, 'getUserInfo').mockResolvedValue(PERSON);
    jest.spyOn(FormUtils, 'getFormsByUserId').mockResolvedValue([]);

    await act(async () => {
      renderWithRouter(
        <UserData.Provider
          value={{
            user: USER_PROFILE,
            signOut,
            userManager
          }}
        >
          <AppRouter />
        </UserData.Provider>,
        { route: APP_URL }
      );
    });

    const pageTitle = screen.getByText('Form I-131');
    expect(pageTitle).not.toBeNull();
  });

  it('renders authenticated admin route', async () => {
    jest
      .spyOn(userManager, 'getUser')
      .mockImplementation(async () => ADMIN_PROFILE);
    jest.spyOn(UserUtils, 'getUserInfo').mockResolvedValue(PERSON);
    jest.spyOn(FormUtils, 'getFormsByUserId').mockResolvedValue([]);

    await act(async () => {
      renderWithRouter(
        <UserData.Provider
          value={{
            user: USER_PROFILE,
            signOut,
            userManager
          }}
        >
          <AppRouter />
        </UserData.Provider>,
        { route: '/admin' }
      );
    });

    const pageTitle = screen.getByText('Form I-131');
    expect(pageTitle).not.toBeNull();
  });

  it('should render a ProtectedRoute', async () => {
    renderWithRouter(
      <UserData.Provider
        value={{
          user: USER_PROFILE,
          signOut,
          userManager
        }}
      >
        <ProtectedRoute>
          <div>This is a test</div>
        </ProtectedRoute>
      </UserData.Provider>
    );

    const testText = screen.getByText(/This is a test/i);
    expect(testText).toBeInTheDocument();
  });

  it('renders authenticated form page', async () => {
    jest
      .spyOn(userManager, 'getUser')
      .mockImplementation(async () => USER_PROFILE);
    jest
      .spyOn(AuthUtils, 'getUserRoles')
      .mockImplementation(() => [AuthUtils.roleAdmin]);

    await act(async () => {
      renderWithRouter(
        <UserData.Provider
          value={{
            user: USER_PROFILE,
            signOut,
            userManager
          }}
        >
          <AppRouter />
        </UserData.Provider>,
        { route: FORM_PAGE_URL }
      );
    });

    const nextButton = await waitFor(() => screen.getByText('Next'));

    expect(nextButton).toBeInTheDocument();
  });

  it('renders authenticated travel categories page', async () => {
    jest
      .spyOn(userManager, 'getUser')
      .mockImplementation(async () => USER_PROFILE);

    const headerText =
      'This form is for applying to U.S. Citizenship and Immigration Services (USCIS) for the following travel documents:';
    await act(async () => {
      renderWithRouter(
        <UserData.Provider
          value={{
            user: USER_PROFILE,
            signOut,
            userManager
          }}
        >
          <AppRouter />
        </UserData.Provider>,
        { route: SELECT_CATEGORY_URL }
      );
    });

    const header = screen.getByText(headerText);
    expect(header).not.toBeNull();
  });

  it('renders authenticated instructions page', async () => {
    jest
      .spyOn(userManager, 'getUser')
      .mockImplementation(async () => USER_PROFILE);

    await act(async () => {
      renderWithRouter(
        <UserData.Provider
          value={{
            user: USER_PROFILE,
            signOut,
            userManager
          }}
        >
          <AppRouter />
        </UserData.Provider>,
        { route: `${INSTRUCTIONS_URL}` }
      );
    });

    const pageTitle = screen.getByText('Instructions');
    expect(pageTitle).not.toBeNull();
  });
});
