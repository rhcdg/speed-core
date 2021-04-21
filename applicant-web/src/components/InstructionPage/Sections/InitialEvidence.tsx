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
    fontWeight: 600
  }
});

const InitialEvidence: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          (1) A copy of any document issued to you by USCIS showing your present
          status, if any, in the United States; and
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (2) An explanation or other evidence showing the circumstances that
          warrant issuance of an Advance Parole Document; or
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (3) If you are an applicant for adjustment of status, a copy of a
          USCIS receipt as evidence that you filed the adjustment application;
          or
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (4) If you are traveling to Canada to apply for an immigrant visa, a
          copy of the U.S. consular letter; or
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (5) If USCIS has deferred action in your case under DACA, you must
          include a copy of the Form I-797, Notice of Action, showing that the
          decision on your Form I-821D was to defer action in your case. If ICE
          deferred action in your case under DACA, submit a copy of the approval
          order, notice or letter issued by ICE.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          You must complete Part 4. of the form indicating how your intended
          travel fits within 1 of the 3 purposes below.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          You must also provide evidence of your reason for travel outside of
          the United States including the dates of travel and the expected
          duration outside the United States. If your advance parole application
          is approved, the validity dates of your Advance Parole Document will
          be for the duration of the documented need for travel. Below are
          examples of acceptable evidence:
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Educational Purposes
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (a) A letter from a school employee acting in an official capacity
          describing the purpose of the travel and explaining why travel is
          required or beneficial; or
          <br />
          (b) A document showing enrollment in an educational program requiring
          travel.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Employment Purposes
        </Typography>
        <Typography variant="body1" className={classes.text}>
          A letter from your employer or a conference host describing the need
          for the travel.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Humanitarian Purposes
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (a) A letter from your physician explaining the nature of your medical
          condition, the specific medical treatment to be sought outside of the
          United States, and a brief explanation why travel outside the U.S. is
          medically necessary; or
          <br />
          (b) Documentation of a family member’s serious illness or death
        </Typography>
      </Grid>
    </Grid>
  );
};

export default InitialEvidence;
