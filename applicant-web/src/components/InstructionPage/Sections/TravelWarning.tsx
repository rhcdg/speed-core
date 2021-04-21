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

const TravelWarning: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.boldOnly}>
          Before you apply for an Advance Parole Document, read the following
          travel warning carefully.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          For any kind of Advance Parole Document provided to you while you are
          in the United States:
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (1) Leaving the United States, even with an Advance Parole Document,
          may impact your ability to return to the United States.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (2) If you use an Advance Parole Document to leave and return to a
          port-of-entry in the United States, you will, upon your return, be an
          “applicant for admission.”
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (3) As an applicant for admission, you will be subject to inspection
          at a port-of-entry, and you may not be admitted if you are found to be
          inadmissible under any applicable provision of INA section 212(a) or
          235 or any other provision of U.S. law regarding denial of admission
          to the United States. If DHS determines that you are inadmissible, you
          may be subject to expedited removal proceedings or to removal
          proceedings before an immigration judge, as authorized by law and
          regulations.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (4) As noted above, issuance of an Advance Parole Document does NOT
          entitle you to parole and does not guarantee that DHS will parole you
          into the United States upon your return.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (5) As noted above, DHS will make a separate discretionary decision
          whether to parole you each time you use an Advance Parole Document to
          return to the United States.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (6) If, upon your return, you are paroled into the United States, you
          will remain an applicant for admission.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (7) As noted above, DHS may revoke or terminate your Advance Parole
          Document at any time, including while you are outside the United
          States. Even if you have already been paroled, upon your return to the
          United States, DHS may also revoke or terminate your parole in
          accordance with 8 CFR 212.5. If you are outside the United States,
          revocation or termination of your Advance Parole Document may preclude
          you from returning to the United States unless you have a valid visa
          or other document that permits you to travel to the United States and
          seek admission.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (8) If you are in the United States when DHS revokes or terminates
          your parole, you will be an unparoled applicant for admission, and may
          be subject to removal as an applicant for admission who is
          inadmissible under INA section 212, rather than as an admitted alien
          who is deportable under INA section 237. In addition to the above, if
          you received deferred action under DACA, you should also be aware of
          the following: (a) Even after USCIS or ICE has deferred action in your
          case under DACA, you should not travel outside the United States
          unless USCIS has approved your application for an Advance Parole
          Document. Deferred action will terminate automatically if you travel
          outside the United States without obtaining an Advance Parole Document
          from USCIS. (b) If you obtain an Advance Parole Document in connection
          with a decision to defer removal in your case under DACA and if, upon
          your return, you are paroled into the United States, your case will
          generally continue to be deferred. The deferral will continue until
          the date specified by USCIS or ICE in the deferral notice given to you
          or until the decision to defer removal action in your case has been
          terminated, whichever is earlier. (c) If you have been ordered
          excluded, deported, or removed, departing from the United States
          without having had your exclusion, deportation, or removal proceedings
          reopened and administratively closed or terminated will result in your
          being considered excluded, deported, or removed, even if USCIS or ICE
          has deferred action in your case under DACA and you have been granted
          advance parole.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          (9) If you depart from the United States before the Advance Parole
          Document is issued, your application for an Advance Parole Document
          will be considered abandoned.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default TravelWarning;
