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

const Decision: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          The decision on Form I-131 involves a determination of whether you
          have established eligibility for the requested document. You will be
          notified of the decision in writing.
        </Typography>
        <Typography variant="body1" className={classes.bold}>
          What If You Claim Nonresident Alien Status on Your Federal Income Tax
          Return?
        </Typography>
        <Typography variant="body1" className={classes.text}>
          If you are an alien who has been admitted as an immigrant or adjusted
          status to that of an immigrant, and are considering the filing of a
          nonresident alien tax return or the non-filing of a tax return on the
          ground that you are a nonresident alien, you should carefully review
          the consequences of such actions under the INA. If you file a
          nonresident alien tax return or do not file a tax return, you may be
          regarded as having abandoned residence in the United States and as
          having lost your lawful permanent resident status under the INA. As a
          consequence, you may be ineligible for a visa or other document for
          which lawful permanent resident aliens are eligible. You may also be
          inadmissible to the United States if you seek admission as a returning
          resident, and you may become ineligible for adjustment of status as a
          lawful permanent resident or naturalization on the basis of your
          original entry.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Decision;
