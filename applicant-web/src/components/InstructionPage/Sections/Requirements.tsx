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
  }
});

const Requirements: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          All applications must include a copy of an official photo identity
          document showing your photo, name, and date of birth. (Examples: Your
          current Employment Authorization Document, if available; a valid
          government issued driver’s license; passport identity page; Form
          I-551, Permanent Resident Card; or any other official identity
          document.) The copy must clearly show the photo and identity
          information.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          NOTE: Form I-94 Arrival-Departure Record is not acceptable as a photo
          identity document.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          You must file your application with all required evidence. Not
          submitting required evidence will delay the issuance of the document
          you are requesting. USCIS may request additional information or
          evidence or may request that you appear at a USCIS office for an
          interview or for fingerprinting.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Requirements;
