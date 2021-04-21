import React from 'react';
import { Grid, makeStyles, Typography } from '@material-ui/core';
import { TravelCategoryCardContents } from '../../utils/formUtils';
import CategoryCard from './CategoryCard';

const useStyles = makeStyles({
  header: {
    padding: '16px',
    fontWeight: 700
  }
});

const TravelCategories: React.FC = () => {
  const classes = useStyles();
  return (
    <div id="main-content" className="main-content">
      <Grid container spacing={4}>
        <Typography variant="h6" className={classes.header}>
          This form is for applying to U.S. Citizenship and Immigration Services
          (USCIS) for the following travel documents:
        </Typography>
        {TravelCategoryCardContents.map(contents => (
          <CategoryCard contents={contents} key={contents.key} />
        ))}
      </Grid>
    </div>
  );
};

export default TravelCategories;
