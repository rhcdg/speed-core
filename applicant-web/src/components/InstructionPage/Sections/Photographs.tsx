import React from 'react';
import { Grid, makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingBottom: '24px'
  },
  boldText: {
    paddingBottom: '24px',
    fontWeight: 600
  }
});

const Photographs: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          You must submit 2 identical color photographs of yourself taken within
          30 days of the filing of this application. The photos must have a
          white to off-white background, be printed on thin paper with a glossy
          finish, and be unmounted and un retouched.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          NOTE: Because of the current USCIS scanning process, if a digital
          photo is submitted, it must be produced from a high-resolution camera
          that has at least 3.5 mega pixels of resolution.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Passport-style photos must be 2” x 2.” The photos must be in color
          with full face, frontal view on a white to off white background. Head
          height should measure 1” to 1 3/8” from top of hair to bottom of chin,
          and eye height is between 1 1/8” to 1 3/8” from bottom of photo. Your
          head must be bare unless you are wearing headwear as required by a
          religious denomination of which you are a member. Using pencil or felt
          pen, lightly print your name and A-Number on the back of the photo.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Photographs;
