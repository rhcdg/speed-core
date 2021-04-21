import React from 'react';
import { Grid, makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingBottom: '24px'
  },
  bold: {
    fontWeight: 600
  }
});

const AcceptedPayment: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          Use the following guidelines when you prepare your checks or money
          orders for the Form I-131 filing fee and biometric services fee:
        </Typography>
        <Typography variant="body1" className={classes.text}>
          1. The check or money order must be drawn on a bank or other financial
          institution located in the United States and must be payable in U.S.
          currency; and
        </Typography>
        <Typography variant="body1" className={classes.text}>
          2. Make the checks or money orders payable to U.S. Department of
          Homeland Security,
          <br />
          NOTE: Spell out U.S. Department of Homeland Security; do not use the
          initials “USDHS” or “DHS.”
        </Typography>
        <Typography variant="body1" className={classes.text}>
          3. If you live outside the United States, contact the nearest U.S.
          Embassy or U.S. Consulate for instructions on the method of payment.
        </Typography>
        <Typography variant="body1" className={classes.bold}>
          Notice to Those Making Payment by Check.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          If you send us a check, USCIS will convert it into an electronic funds
          transfer (EFT). This means we will copy your check and use the account
          information on it to electronically debit your account for the amount
          of the check. The debit from your account will usually take 24 hours
          and your bank will show it on your regular account statement.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          You will not receive your original check back. We will destroy your
          original check, but will keep a copy of it. If USCIS cannot process
          the EFT for technical reasons, you authorize us to process the copy in
          place of your original check. If your check is returned as unpayable,
          USCIS will re-submit the payment to the financial institution one
          time. If the check is returned as unpayable a second time, we will
          reject your application and charge you a returned check fee.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default AcceptedPayment;
