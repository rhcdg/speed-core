import React, { useEffect, useState } from 'react';
import './navbar.scss';
import { User, UserManager } from 'oidc-client';
import {
  Button,
  Grid,
  Typography,
  Dialog,
  DialogContent,
  DialogTitle,
  DialogContentText,
  DialogActions,
  FadeProps
} from '@material-ui/core';

import favicon57 from '../../assets/img/favicon-57.png';
import BlueNavDHSSeal from '../../assets/img/BlueNavDHSSeal.png';
import { isTokenActive, isTokenAdmin } from '../../utils/authenticationUtils';
import { ADMIN_URL } from '../../utils/routeUrls';

interface NavBarProps {
  signOut?: () => void;
  user?: User | null;
  userManager?: UserManager;
}

const NavBar: React.FC<NavBarProps> = ({ signOut, user, userManager }) => {
  const [authenticatedUser, setAuthenticatedUser] = useState<
    User | null | undefined
  >(user);

  const [open, setOpen] = useState<boolean>(false);

  useEffect(() => {
    if (userManager) {
      userManager
        .getUser()
        .then(fetchedUser => setAuthenticatedUser(fetchedUser))
        .catch(() => {
          // TODO
        });
    }
  }, [userManager]);

  return (
    <div>
      <header>
        <div className="usa-banner">
          <div className="container">
            <img
              className="icon "
              alt="US Flag"
              title="US Flag"
              src={favicon57}
            />
            <p>Official website of the Department of Homeland Security</p>
          </div>
        </div>
        <nav className="navbar navbar-expand-md navbar-dark fixed-top">
          <div className="container">
            <div className="navbar-header">
              <img
                className="logo "
                alt="USCIS"
                title="USCIS Logo"
                src={BlueNavDHSSeal}
              />
            </div>
            <button
              className="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#navbarCollapse"
              aria-controls="navbarCollapse"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon" />
            </button>
            <div className="collapse navbar-collapse" id="navbarCollapse">
              <Grid container direction="column" alignItems="flex-end">
                <Grid>
                  <ul className="navbar-nav ml-auto">
                    {isTokenAdmin(authenticatedUser) ? (
                      <li className="nav-item">
                        <a id="nav-admin" className="nav-link" href={ADMIN_URL}>
                          Admin
                        </a>
                      </li>
                    ) : (
                      <div> </div>
                    )}
                    {isTokenActive(authenticatedUser) ? (
                      <li className="nav-item">
                        <Button
                          data-testid="handler-click"
                          id="nav-log-out"
                          className="nav-link navbar-nav"
                          onClick={() => setOpen(true)}
                        >
                          Logout
                        </Button>
                      </li>
                    ) : (
                      <div> </div>
                    )}
                  </ul>
                </Grid>
                <Grid>
                  <Typography variant="h5" className="nav-title">
                    Case Management Portal
                  </Typography>
                </Grid>
              </Grid>
            </div>
          </div>
        </nav>
      </header>
      <Dialog
        open={open}
        TransitionProps={{ role: 'presentation' } as FadeProps}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">Logout</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure you want to logout?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => setOpen(false)}
            color="primary"
            data-testid="logout-dialog-nope"
          >
            No
          </Button>
          <Button
            onClick={() => signOut && signOut()}
            data-testid="logout-dialog-confirm"
            color="primary"
            autoFocus
          >
            Yes
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default NavBar;
