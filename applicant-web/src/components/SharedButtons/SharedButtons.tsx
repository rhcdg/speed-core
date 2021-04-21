import React from 'react';
import { Button, Divider, Grid, Link, makeStyles } from '@material-ui/core';
import clsx from 'clsx';
import {
  SELECT_CATEGORY_URL,
  FORM_PAGE_URL,
  INSTRUCTIONS_URL
} from '../../utils/routeUrls';

const useStyles = makeStyles({
  buttonContainer: {
    justifyContent: 'space-between'
  },
  buttonBorder: {
    backgroundColor: '#3f51b5',
    height: '4px',
    margin: '8px'
  },
  buttons: {
    margin: '8px 8px 0px 12px'
  },
  active: {
    color: '#3f51b5'
  }
});

interface SharedButtonsProps {
  page: 'instructions' | 'form';
}

const SharedButtons: React.FC<SharedButtonsProps> = ({ page }) => {
  const classes = useStyles();

  const isActive = (linkTarget: string) => linkTarget === page;

  return (
    <Grid container item xs={4} className={classes.buttonContainer}>
      <Grid item xs={3}>
        <Link href={SELECT_CATEGORY_URL}>
          <Button className={classes.buttons}>Travel Categories</Button>
        </Link>
      </Grid>
      <Grid item xs={3}>
        <Link href={INSTRUCTIONS_URL}>
          <Button
            className={clsx(
              classes.buttons,
              isActive('instructions') && classes.active
            )}
          >
            Instructions
          </Button>
          {isActive('instructions') && (
            <Divider
              className={classes.buttonBorder}
              data-testid="instructions-active"
            />
          )}
        </Link>
      </Grid>
      <Grid item xs={3}>
        <Link href={FORM_PAGE_URL}>
          <Button
            className={clsx(
              classes.buttons,
              isActive('form') && classes.active
            )}
          >
            Application
          </Button>
          {isActive('form') && (
            <Divider
              className={classes.buttonBorder}
              data-testid="form-active"
            />
          )}
        </Link>
      </Grid>
    </Grid>
  );
};

export default SharedButtons;
