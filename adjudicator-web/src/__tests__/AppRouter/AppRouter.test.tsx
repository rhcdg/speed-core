import React, { ReactElement } from 'react';
import ReactGA from 'react-ga';
import {
  cleanup,
  fireEvent,
  render,
  RenderResult,
  screen
} from '@testing-library/react';
import { within } from '@testing-library/dom';
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
import { roleAdjudicator } from '../../utils/authenticationUtils';

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
    'cognito:groups': [roleAdjudicator]
  },
  expires_at: 99999999999,
  state: '',
  expires_in: 99999999999,
  expired: false,
  scopes: [],
  toStorageString: (): string => ''
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
      fireEvent.click(renderResult.getByTestId('logout-dialog-confirm'));
    }

    expect(userManager.removeUser).toHaveBeenCalledTimes(1);
    expect(userManager.signoutRedirect).toHaveBeenCalledTimes(1);
    expect(userManager.getUser).toHaveBeenCalledTimes(3);
  });

  it('renders authenticated cases route', async () => {
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
        { route: '/cases' }
      );
    });

    const tabs = screen.getByTestId('case-management-tabs');
    const screenText = within(tabs).getByText('New Cases');
    expect(screenText).not.toBeNull();
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
});
