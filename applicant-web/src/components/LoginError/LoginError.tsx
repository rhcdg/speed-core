import React from 'react';
import { Button } from '@material-ui/core';

interface LoginErrorProps {
  signOut: () => void;
}

const LoginError: React.FC<LoginErrorProps> = ({ signOut }) => {
  return (
    <div id="main-content" className="main-content">
      <div className="card">
        <div className="card-body">
          Please log in as a user with the Applicant role to use this
          application. Please{' '}
          <Button
            color="primary"
            data-testid="handler-click"
            onClick={() => signOut && signOut()}
          >
            Logout
          </Button>
        </div>
      </div>
    </div>
  );
};

export default LoginError;
