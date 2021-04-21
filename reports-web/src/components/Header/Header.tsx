import React from 'react';
import { Typography, Divider } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { indigo } from '@material-ui/core/colors';

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    width: '100%',
    margin: theme.spacing(2)
  },
  text: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(2),
    color: indigo[900]
  }
}));

interface HeaderProps {
  text: string;
}

const Header: React.FC<HeaderProps> = ({ text }) => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Typography className={classes.text} variant="h4">
        {text}
      </Typography>
      <Divider variant="fullWidth" />
    </div>
  );
};

export default Header;
