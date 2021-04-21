import { Button, Grid, Link, makeStyles, Typography } from '@material-ui/core';
import { User } from 'oidc-client';
import React from 'react';
import { getFormsByUserId } from '../../utils/formService';
import { I131FormValues, IN_PROGRESS, SUBMITTED } from '../../utils/formUtils';
import { SELECT_CATEGORY_URL } from '../../utils/routeUrls';
import { getUserInfo } from '../../utils/userService';
import { Person } from '../../utils/usersUtils';
import HomeTable, { FormTableRow } from './HomeTable';

const useStyles = makeStyles({
  welcome: {
    fontWeight: 500
  },
  welcomeContainer: {
    paddingBottom: '24px'
  },
  button: {
    fontWeight: 500,
    padding: '0px 36px 0px 36px'
  }
});

interface LandingPageProps {
  user?: User | null;
}

const mapFormToTableRow = (form: I131FormValues): FormTableRow => {
  const { formNumber, submissionDate, submissionStatus } = form;
  return {
    formNumber,
    submissionDate,
    submissionStatus
  };
};

const LandingPage: React.FC<LandingPageProps> = ({ user }) => {
  const classes = useStyles();
  const [forms, setForms] = React.useState<I131FormValues[]>([]);
  const [submittedRows, setSubmittedRows] = React.useState<FormTableRow[]>([]);
  const [inProgressRows, setInProgressRows] = React.useState<FormTableRow[]>(
    []
  );
  const [userData, setUserData] = React.useState<Person>();

  React.useEffect(() => {
    if (user) {
      getUserInfo(user.profile['cognito:username'])
        .then(response => {
          setUserData(response);
        })
        .catch(err => {
          throw new Error(err);
        });

      getFormsByUserId(user.profile['cognito:username'])
        .then(response => {
          setForms(response);
        })
        .catch(err => {
          throw new Error(err);
        });
    }
  }, [user]);

  React.useEffect(() => {
    if (forms.length) {
      const inProgress: FormTableRow[] = [];
      const submitted: FormTableRow[] = [];
      forms.forEach(form => {
        if (form.submissionStatus === IN_PROGRESS) {
          inProgress.push(mapFormToTableRow(form));
        }
        if (form.submissionStatus === SUBMITTED) {
          submitted.push(mapFormToTableRow(form));
        }
      });

      setSubmittedRows(submitted);
      setInProgressRows(inProgress);
    }
  }, [forms]);

  return (
    <div id="main-content" className="main-content">
      <Grid container spacing={4} justify="flex-end">
        <Grid
          container
          justify="space-between"
          className={classes.welcomeContainer}
        >
          <Typography variant="h4" className={classes.welcome}>
            Welcome {userData && userData.name}
          </Typography>
          <Link href={SELECT_CATEGORY_URL}>
            <Button variant="contained" color="primary">
              Start a New Form
            </Button>
          </Link>
        </Grid>
        <Grid container spacing={4}>
          <HomeTable tableType="submitted" rows={submittedRows} />
          <HomeTable tableType="in-progress" rows={inProgressRows} />
        </Grid>
      </Grid>
    </div>
  );
};

export default LandingPage;
