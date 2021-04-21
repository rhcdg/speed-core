import React from 'react';
import { Grid, Link, makeStyles, Typography } from '@material-ui/core';

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
  },
  boldOnly: {
    fontWeight: 600
  }
});

const ProcessingInformation: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.boldOnly}>
          Where to File
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Please see our website at&nbsp;
          <Link href="www.uscis.gov/I-131">www.uscis.gov/I-131</Link>&nbsp;or
          call our USCIS National Customer Service Center at 1-800-375-5283 for
          the most current information about where to file this benefit request.
          For TTY (hearing impaired) call: 1-800-767-1833.
        </Typography>
        <Typography variant="body1" className={classes.boldOnly}>
          Address Changes
        </Typography>
        <Typography variant="body1" className={classes.text}>
          If you have changed your address, you must inform USCIS of your new
          address. For information on filing a change of address go to the USCIS
          website at&nbsp;
          <Link href="www.uscis.gov/addresschange">
            www.uscis.gov/addresschange
          </Link>
          &nbsp;or contact the USCIS National Customer Service Center at
          1-800-375-5283. For TTY (hearing impaired) call: 1-800-767-1833.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          NOTE: Do not submit a change of address to the USCIS Lockbox
          facilities because the USCIS Lockbox facilities do not process change
          of address requests.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Any Form I-131 that is not signed or accompanied by the correct fees
          will be rejected with a notice that Form I-131 is deficient. You may
          correct the deficiency and resubmit Form I-131. An application or
          petition is not considered properly filed until accepted by USCIS.
        </Typography>
        <Typography variant="body1" className={classes.boldOnly}>
          Initial Processing
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Once a Form I-131 has been accepted, it will be checked for
          completeness, including submission of the required initial evidence.
          If you do not completely fill out the form, or file it without
          required initial evidence, you will not establish a basis for
          eligibility, and we may deny your Form I-131.
        </Typography>
        <Typography variant="body1" className={classes.boldOnly}>
          Requests for More Information, Including Biometrics, or Interview
        </Typography>
        <Typography variant="body1" className={classes.text}>
          We may request more information or evidence, or we may request that
          you appear at a USCIS office, U.S. Embassy, or U.S. Consulate for an
          interview. We may also request that you submit the originals of any
          copy. We will return these originals when they are no longer required.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          At the time of any interview or other appearance at a USCIS office,
          U.S. Embassy, or U.S. Consulate, USCIS may require you to provide
          biometrics information (for example, photographs, fingerprints) to
          verify your identity and update your background information.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default ProcessingInformation;
