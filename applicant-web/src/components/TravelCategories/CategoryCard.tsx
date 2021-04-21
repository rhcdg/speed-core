import React from 'react';
import {
  Button,
  Container,
  Grid,
  Link,
  makeStyles,
  Paper,
  Typography
} from '@material-ui/core';
import { FORM_PAGE_URL, INSTRUCTIONS_URL } from '../../utils/routeUrls';

const useStyles = makeStyles({
  paper: {
    border: '2px solid',
    borderColor: 'lightgray',
    boxShadow: 'none'
  },
  header: {
    padding: '16px',
    fontWeight: 700
  },
  body: {
    padding: '0px 16px 16px 16px'
  },
  buttons: {
    marginLeft: '0px',
    padding: '16px'
  },
  instructionButton: {
    margin: '0px 16px 0px 0px',
    fontWeight: 600,
    border: '2px solid'
  },
  applicationButton: {
    fontWeight: 600
  }
});

interface CategoryCardProps {
  contents: {
    key: string;
    title: string;
    text: string;
    disabled: boolean;
  };
}
const CategoryCard: React.FC<CategoryCardProps> = ({
  contents: { title, text, disabled }
}) => {
  const classes = useStyles();

  return (
    <Grid item xs={12}>
      <Paper className={classes.paper}>
        <Typography variant="h6" className={classes.header}>
          {title}
        </Typography>
        <Typography variant="body1" className={classes.body}>
          {text}
        </Typography>
        <Container className={classes.buttons}>
          <Link href={`${INSTRUCTIONS_URL}`}>
            <Button
              variant="outlined"
              color="primary"
              className={classes.instructionButton}
              disabled={disabled}
            >
              Instructions
            </Button>
          </Link>
          <Link href={`${FORM_PAGE_URL}`}>
            <Button
              variant="contained"
              color="primary"
              className={classes.applicationButton}
              disabled={disabled}
            >
              Application
            </Button>
          </Link>
        </Container>
      </Paper>
    </Grid>
  );
};

export default CategoryCard;
