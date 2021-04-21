import React from 'react';
import { Grid, Link, makeStyles, Typography } from '@material-ui/core';
import InfoCard from '../../InfoCard/InfoCard';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingTop: '24px'
  },
  boldText: {
    paddingTop: '24px',
    fontWeight: 600
  }
});

const infoCardContents = (
  <div>
    NOTE: Do not file Form I-131 if you are seeking release from immigration
    custody and you want to remain in the United States as a parolee. You should
    contact ICE about your request at{' '}
    <Link href="www.ice.gov/contact/ero">www.ice.gov/contact/ero</Link>
  </div>
);

const Overview: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <InfoCard contents={infoCardContents} parent="instructions" />
        <Typography variant="body1" className={classes.text}>
          Parole allows an alien to physically enter into the United States for
          a specific purpose. An individual who has been “paroled” has not been
          admitted to the United States and remains an “applicant for admission”
          even while paroled.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          DHS, as a matter of discretion, may issue an Advance Parole Document
          to authorize an alien to appear at a port-of-entry to seek parole into
          the United States. The document may be accepted by a transportation
          company in lieu of a visa as an authorization for the holder to travel
          to the United States. An Advance Parole Document is not issued to
          serve in place of any required passport.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          WARNING: The document does not entitle you to be paroled into the
          United States; a separate discretionary decision on a request for
          parole will be made when you arrive at a port-of-entry upon your
          return.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          WARNING: DHS may revoke or terminate your Advance Parole Document at
          any time, including while you are outside the United States, in which
          event you may be unable to return to the United States unless you have
          a valid visa or other document that permits you to travel to the
          United States and seek admission.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          NOTE: Generally, if you are in the United States and have applied for
          adjustment of status to that of a lawful permanent resident, your
          application will be deemed abandoned if you leave the United States
          without first obtaining an Advance Parole Document. Your application
          for adjustment of status generally will not be deemed abandoned, even
          if you do not apply for an Advance Parole Document before traveling
          abroad while an adjustment application is pending, if you currently
          are in one of the following nonimmigrant classifications, and remain
          eligible for and would be admissible in one of the following
          categories upon applying for admission at a port-of-entry:
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          • An H-1 temporary worker, or H-4 spouse or child of an H-1;
          <br />• An L-1 intracompany transferee, or L-2 spouse or child of an
          L-1;
          <br />• A K-3 spouse, or K-4 child of a U.S. citizen; or
          <br />• A V-1 spouse, or V-2/V-3 child of a lawful permanent resident.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          NOTE: Upon returning to the United States, most individuals must
          present a valid H, L, K, or V nonimmigrant visa and must continue to
          be otherwise admissible. If you do not have a valid or unexpired H, L,
          K, or V nonimmigrant visa, then you generally need to obtain an H, L,
          K, or V nonimmigrant visa at a U.S. Department of State (DOS) visa
          issuing post. Individuals will need a valid nonimmigrant visa, advance
          parole, or other travel document to present for reentry.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Overview;
