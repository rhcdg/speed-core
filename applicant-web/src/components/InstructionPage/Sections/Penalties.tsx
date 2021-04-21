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

const Penalties: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.bold}>
          Invalidation of Travel Document
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Any travel document obtained by making a material false representation
          or concealment in this application will be invalid. A travel document
          will also be invalid if you are ordered removed or deported from the
          United States.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          If you knowingly and willfully falsify or conceal a material fact or
          submit a false document with this request, we will deny your Form
          I-131 and may deny any other immigration benefit. In addition, you
          will face severe penalties provided by law and may be subject to
          criminal prosecution.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Penalties;
