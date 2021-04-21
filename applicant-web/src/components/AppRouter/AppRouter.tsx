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
import {
  APP_URL,
  LOGIN_URL,
  ADMIN_URL,
  FORM_PAGE_URL,
  SELECT_CATEGORY_URL,
  INSTRUCTIONS_URL
} from '../../utils/routeUrls';
import NavBar from '../NavBar/NavBar';
import LandingPage from '../LandingPage/LandingPage';
import { environment } from '../../environments/environment';
import MainPage from '../MainPage/MainPage';
import {
  hasRole,
  isTokenActive,
  roleApplicant
} from '../../utils/authenticationUtils';
import FormPage from '../FormPage/FormPage';
import TravelCategories from '../TravelCategories/TravelCategories';
import InstructionPage from '../InstructionPage/InstructionPage';
import LoginError from '../LoginError/LoginError';

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

export const ProtectedRoute: React.FunctionComponent<RouteProps> = ({
  children,
  ...rest
}) => {
  return (
    <UserData.Consumer>
      {({ user }) => (
        <Route
          {...rest}
          render={({ location }) =>
            isTokenActive(user) && hasRole(user, roleApplicant) ? (
              children
            ) : (
              <Redirect
                to={{
                  pathname: '/login-error',
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

      <ProtectedRouteAuthenticator exact path={APP_URL}>
        <UserData.Consumer>
          {({ user }) => (
            <>
              <NavBar user={user} signOut={signOut} />
              <LandingPage user={user} />
            </>
          )}
        </UserData.Consumer>
      </ProtectedRouteAuthenticator>

      <ProtectedRouteAuthenticator exact path={FORM_PAGE_URL}>
        <UserData.Consumer>
          {({ user }) => (
            <>
              <NavBar user={user} signOut={signOut} formData />
              <FormPage />
            </>
          )}
        </UserData.Consumer>
      </ProtectedRouteAuthenticator>

      <ProtectedRouteAuthenticator exact path={SELECT_CATEGORY_URL}>
        <UserData.Consumer>
          {({ user }) => (
            <>
              <NavBar user={user} signOut={signOut} formData />
              <TravelCategories />
            </>
          )}
        </UserData.Consumer>
      </ProtectedRouteAuthenticator>

      <ProtectedRouteAuthenticator path={INSTRUCTIONS_URL}>
        <UserData.Consumer>
          {({ user }) => (
            <>
              <NavBar user={user} signOut={signOut} formData />
              <InstructionPage />
            </>
          )}
        </UserData.Consumer>
      </ProtectedRouteAuthenticator>

      <ProtectedRouteAuthenticator exact path={ADMIN_URL}>
        <UserData.Consumer>
          {({ user }) => (
            <>
              <NavBar user={user} signOut={signOut} />
              <LandingPage user={user} />
            </>
          )}
        </UserData.Consumer>
      </ProtectedRouteAuthenticator>

      <Route path="/login-error">
        <NavBar userManager={userManager} signOut={signOut} />
        <LoginError signOut={signOut} />
      </Route>

      <Route path="/">
        <NavBar userManager={userManager} signOut={signOut} />
        <MainPage />
      </Route>
    </Switch>
  );
};
