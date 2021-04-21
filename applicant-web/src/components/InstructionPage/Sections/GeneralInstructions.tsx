import React from 'react';
import { Grid, makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingBottom: '24px'
  },
  boldText: {
    fontWeight: 600
  }
});

const GeneralInstructions: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          If you are completing this form on a computer, the data you enter will
          be captured using 2D barcode technology. This capture will ensure that
          the data you provide is accurately entered into USCIS systems. As you
          complete each field, the 2D barcode field at the bottom of each page
          will shift as data is captured. Upon receipt of your form, USCIS will
          use the 2D barcode to extract the data from the form. Please do not
          damage the 2D barcode (puncture, staple, spill on, write on, etc.) as
          this could affect the ability of USCIS to timely process your form.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          USCIS provides most forms in PDF format free of charge through the
          USCIS website. In order to view, print, or fill out our forms, you
          should use the latest version of Adobe Reader, which can be downloaded
          for free at http://get.adobe.com/reader/.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Each application must be properly signed and accompanied by the
          appropriate fee. (See the What is the Filing Fee section of these
          Instructions.) A photocopy of a signed application or a typewritten
          name in place of a signature is not acceptable. If you are under 14
          years of age, your parent or legal guardian may sign the application
          on your behalf.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Evidence
        </Typography>
        <Typography variant="body1" className={classes.text}>
          You must submit all required initial evidence along with all the
          supporting documentation with your application at the time of filing.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Biometrics Services Appointment
        </Typography>
        <Typography variant="body1" className={classes.text}>
          After receiving your application and ensuring completeness, USCIS will
          inform you in writing when to go to your local USCIS Application
          Support Center (ASC) for your biometrics services appointment. Failure
          to attend the biometrics services appointment may result in denial of
          your application.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Copies
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Unless specifically required that an original document be filed with
          an application, a legible photocopy may be submitted. Original
          documents submitted when not required may remain a part of the record,
          and will not be automatically returned to you.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Translations
        </Typography>
        <Typography variant="body1" className={classes.text}>
          Any document containing foreign language submitted to USCIS must be
          accompanied by a full English language translation which the
          translator has certified as complete and accurate, and by the
          translator’s certification that he or she is competent to translate
          from the foreign language into English.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default GeneralInstructions;
