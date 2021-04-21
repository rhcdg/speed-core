import React from 'react';
import { Grid, makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingBottom: '24px'
  }
});

const Exceptions: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          To request expedited processing of an application for a Reentry
          Permit, a Refugee Travel Document, or an Advance Parole Document for
          an individual outside the United States, other than under one of the
          Family Reunification Parole policies, type or print the word EXPEDITE
          in the top right corner of the application in black ink. USCIS
          recommends that you provide e-mail addresses and a fax number with any
          expedite request for a Reentry Permit, Refugee Travel Document, or
          Advance Parole Document.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Include a written explanation of the reason for the request to
          expedite with any supporting evidence available. The burden is on the
          applicant to demonstrate that one or more of the expedite criteria
          have been met. The criteria are as follows:
          <br />
          a. Severe financial loss to company or individual;
          <br />
          b. Extreme emergent situation;
          <br />
          c. Humanitarian situation; or
          <br />
          d. Non-profit status of requesting organization in furtherance of the
          cultural and social interests of the United States Department of
          Defense or National Interest Situation. (Note: The request must come
          from an official United States Government entity and state that a
          delay will be detrimental to the U.S. Government.)
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Exceptions;
