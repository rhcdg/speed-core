import React from 'react';
import { Typography, Divider, Grid, makeStyles } from '@material-ui/core';

const useStyles = makeStyles({
  root: {
    width: '100%',
    margin: '16px 0px'
  },
  text: {
    fontWeight: 600,
    marginTop: '24px'
  },
  divider: {
    height: '4px',
    backgroundColor: 'gray',
    margin: '16px 8px 32px 0px'
  }
});

const Header: React.FC = () => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Grid item xs={12}>
        <Typography variant="h4" className={classes.text}>
          Advance Parole | Applying Inside the United States
        </Typography>
        <Divider className={classes.divider} />
      </Grid>
    </div>
  );
};

export default Header;
