import React, { ReactNode } from 'react';
import { Card, Grid, makeStyles, Typography } from '@material-ui/core';
import InfoIcon from '@material-ui/icons/Info';

const useStyles = makeStyles({
  infoCard: {
    borderLeft: 'thick solid blue',
    backgroundColor: 'aliceblue',
    padding: '16px',
    marginBottom: '24px'
  },
  cardTextInstructions: {
    marginLeft: '-4em'
  },
  cardTextForm: {
    marginLeft: '-6.5em'
  }
});

interface InfoCardProps {
  contents: ReactNode;
  parent: string;
}

const InfoCard: React.FC<InfoCardProps> = ({ contents, parent }) => {
  const classes = useStyles();

  return (
    <Card className={classes.infoCard}>
      <Grid container>
        <Grid item xs={1}>
          <InfoIcon color="primary" />
        </Grid>
        <Grid
          item
          xs={11}
          className={
            parent === 'form'
              ? classes.cardTextForm
              : classes.cardTextInstructions
          }
        >
          <Typography variant="body1">{contents}</Typography>
        </Grid>
      </Grid>
    </Card>
  );
};

export default InfoCard;
