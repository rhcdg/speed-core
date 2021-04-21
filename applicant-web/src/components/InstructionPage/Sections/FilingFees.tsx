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

const FilingFees: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          Advance Parole Document for Individuals Who Are Currently in the
          United States (including individuals whose cases were deferred
          pursuant to DACA): The filing fee for an Advance Parole Document for
          an individual who is currently in the United States is $575. The
          biometrics services fee is not required.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          NOTE: If you filed Form I-485 on or after July 30, 2007, and you paid
          the Form I-485 application fee required, then no fee is required to
          file a request for an Advance Parole Document or Refugee Travel
          Document on Form I-131 if your Form I-485 is still pending, if:
        </Typography>
        <Typography variant="body1" className={classes.text}>
          1. You now hold U.S. refugee or asylee status, and are applying for a
          Refugee Travel Document or
          <br />
          2. You are applying for an Advance Parole Document to allow you to
          return to the United States after temporary foreign travel (see Part
          2. Application Type, Item Number 1.d. of Form I-131).
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Under these circumstances, you may file Form I-131 together with your
          Form I-485, or you may submit Form I-131 at a later date. If you file
          Form I-131 separately, you must also submit a copy of your Form I-797,
          Notice of Action, receipt as evidence that you filed and paid the fee
          for Form I-485 required on or after July 30, 2007.
        </Typography>
        <Typography variant="body1" className={classes.boldOnly}>
          Replacement Travel Document
        </Typography>
        <Typography variant="body1" className={classes.text}>
          If you are filing to replace a travel document that was lost, stolen,
          mutilated, or contains erroneous information, such as a misspelled
          name, a filing fee is required.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          NOTE: If you are requesting a replacement Advance Parole Document as
          an adjustment applicant filed under the fee structure implemented July
          30, 2007, then the full filing fee will be required; however, no
          biometrics services fee is required.
        </Typography>
        <Typography variant="body1" className={classes.boldOnly}>
          Incorrect Card
        </Typography>
        <Typography variant="body1" className={classes.text}>
          No fee is required if you are filing to correct a USCIS error on your
          travel document. If USCIS did not cause the error, you must pay the
          application fees. NOTE: The filing fee and biometric services fee are
          not refundable, regardless of any action USCIS takes on this
          application.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          DO NOT MAIL CASH. You must submit all fees in the exact amounts.
        </Typography>
        <Typography variant="body1" className={classes.boldOnly}>
          How To Check If the Fees Are Correct
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Form I-131’s filing fee and biometric services fees are current as of
          the edition date in the upper right corner of this page. However,
          because USCIS fees change periodically, you can verify that the fees
          are correct by following one of the steps below.
          <br />
          1. Visit the USCIS website at&nbsp;
          <Link href="www.uscis.gov">www.uscis.gov</Link>, select “FORMS,” and
          check the appropriate fee; or
          <br />
          2. Call the USCIS National Customer Service Center at 1-800-375-5283
          and ask for fee information. For TTY (deaf or hard of hearing) call:
          1-800-767-1833.
        </Typography>
        <Typography variant="body1" className={classes.boldOnly}>
          Fee Waiver
        </Typography>
        <Typography variant="body1" className={classes.text}>
          You may be eligible for a fee waiver under 8 CFR 103.7(c). If you
          believe you are eligible for a fee waiver, complete Form I-912,
          Request for Fee Waiver (or a written request), and submit it and any
          required evidence of your inability to pay the filing fee with this
          application. You can review the fee waiver guidance at&nbsp;
          <Link href="www.uscis.gov/feewaiver">www.uscis.gov/feewaiver</Link>.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default FilingFees;
