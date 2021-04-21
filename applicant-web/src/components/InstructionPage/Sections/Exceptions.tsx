import React from 'react';
import { Grid, makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingTop: '24px'
  },
  boldText: {
    paddingTop: '24px',
    fontWeight: 600
  },
  boldOnly: {
    fontWeight: 600
  }
});

const Exceptions: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.boldOnly}>
          If you are in the United States and seek an Advance Parole Document, a
          document may not be issued to you if:
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (1) You hold a nonimmigrant status, such as J-1, that is subject to
          the 2-year foreign residence requirement as a result of that status.
          Exception: If you are someone who was subject to this requirement but
          are now eligible to apply for adjustment of status to lawful permanent
          resident, USCIS may consider your application for advance parole; or
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (2) You are in exclusion, deportation, removal, or rescission
          proceedings, unless you have received deferred action under DACA. You
          may, however, request parole from ICE.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          WARNING: If you depart from the United States before the Advance
          Parole Document is issued, your application for an Advance Parole
          Document will be considered abandoned.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Exceptions;
