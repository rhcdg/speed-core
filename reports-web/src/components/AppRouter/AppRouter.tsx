import * as React from 'react';
import ReactGA from 'react-ga';

import {
  Route,
  Switch,
  RouteProps,
  Redirect,
  RouteComponentProps,
  useHistory
} from 'react-router-dom';
import { makeAuthenticator, Callback, UserData } from 'react-oidc';
import { User, UserManager } from 'oidc-client';
import { APP_URL, LOGIN_URL } from '../../utils/routeUrls';
import NavBar from '../NavBar/NavBar';
import LandingPage from '../LandingPage/LandingPage';
import { environment } from '../../environments/environment';
import MainPage from '../MainPage/MainPage';
import {
  hasRole,
  isTokenActive,
  roleAdmin
} from '../../utils/authenticationUtils';

export const userManager: UserManager = new UserManager({
  ...environment.cognito,
  redirect_uri: `${window.location.protocol}//${window.location.host}/callback`,
  metadata: {
    ...environment.cognito.metadata,
    end_session_endpoint: `${environment.cognito.metadata.end_session_endpoint}?client_id=${environment.cognito.client_id}&response_type=${environment.cognito.response_type}&logout_uri=${window.location.protocol}//${window.location.host}`
  }
});

const signOut = async () => {
  if (userManager) {
    userManager.removeUser();
    userManager.signoutRedirect();
    userManager.getUser();
  }
};

export const getAccessToken = async (): Promise<string | undefined> => {
  const user: User | null = await userManager.getUser();
  return user ? user.access_token : undefined;
};

interface ProtectedRouteProps extends RouteProps {
  role?: string;
}

export const ProtectedRoute: React.FunctionComponent<ProtectedRouteProps> = ({
  role,
  children,
  ...rest
}) => {
  return (
    <UserData.Consumer>
      {({ user }) => (
        <Route
          {...rest}
          render={({ location }) =>
            isTokenActive(user) && hasRole(user, role) ? (
              children
            ) : (
              <Redirect
                to={{
                  pathname: '/',
                  state: { from: location }
                }}
              />
            )
          }
        />
      )}
    </UserData.Consumer>
  );
};

const ProtectedRouteAuthenticator = makeAuthenticator({
  userManager
})(ProtectedRoute);

export const AppRouter: React.FC = () => {
  const history = useHistory();

  ReactGA.initialize(`${environment.analytics.measurement_id}`);
  ReactGA.pageview(history.location.pathname);

  return (
    <Switch>
      <Route
        path="/callback"
        render={(routeProps: RouteComponentProps) => (
          <Callback
            onSuccess={() => {
              routeProps.history.replace(APP_URL);
            }}
            onError={err => {
              // eslint-disable-next-line no-console
              console.error(err);
            }}
            userManager={userManager}
          />
        )}
      />

      <ProtectedRouteAuthenticator exact path={LOGIN_URL}>
        <Redirect to="/" />
      </ProtectedRouteAuthenticator>

      <ProtectedRouteAuthenticator exact path={APP_URL} role={roleAdmin}>
        <UserData.Consumer>
          {({ user }) => (
            <>
              <NavBar user={user} signOut={signOut} />
              <LandingPage />
            </>
          )}
        </UserData.Consumer>
      </ProtectedRouteAuthenticator>

      <Route path="/">
        <NavBar userManager={userManager} signOut={signOut} />
        <MainPage />
      </Route>
    </Switch>
  );
};
