import React from 'react';
import { Button, Grid, makeStyles, Snackbar } from '@material-ui/core';
import MuiAlert, { AlertProps, Color } from '@material-ui/lab/Alert';
import { useFormikContext } from 'formik';
import { I131FormValues } from '../../../utils/formUtils';
import InfoCard from '../../InfoCard/InfoCard';
import { submitForm } from '../../../utils/formService';
import TravelInformation from '../TravelInformation/TravelInformation';
import PersonalInformation from '../PersonalInformation/PersonalInformation';
import Signature from '../Signature/Signature';
import SupportingEvidence from '../SupportingEvidence/SupportingEvidence';

const useStyles = makeStyles({
  submit: {
    width: '100%'
  }
});

const cardContent =
  'Please make sure all the information below is complete and correct before submitting your application.';

function Alert(props: AlertProps) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

interface ReviewAndSubmitProps {
  form?: I131FormValues;
}

const ReviewAndSubmit: React.FC<ReviewAndSubmitProps> = ({ form }) => {
  const { validateForm, dirty, isValid } = useFormikContext();
  const classes = useStyles();
  const [open, setOpen] = React.useState<boolean>(false);
  const [message, setMessage] = React.useState<string>('');
  const [severity, setSeverity] = React.useState<Color>();

  const handleSubmit = async () => {
    form &&
      validateForm(form) &&
      (await submitForm(form)
        .then(response => {
          if (response.status === 200) {
            setMessage('Form data successfully saved');
            setSeverity('success' as Color);
          } else {
            setMessage('Submission failed');
            setSeverity('error' as Color);
          }
          setOpen(true);
        })
        .catch(err => {
          throw new Error(err);
        }));
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Grid container>
      <Grid item xs={12}>
        <InfoCard contents={cardContent} parent="form" />
      </Grid>
      <Grid item xs={12}>
        <PersonalInformation readOnly />
      </Grid>
      <Grid item xs={12}>
        <TravelInformation readOnly />
      </Grid>
      <Grid item xs={12}>
        <SupportingEvidence readOnly />
      </Grid>
      <Grid item xs={12}>
        <Signature readOnly />
      </Grid>
      <Grid item xs={4} />
      <Grid item xs={4}>
        <Button
          variant="contained"
          color="primary"
          onClick={handleSubmit}
          className={classes.submit}
          disabled={dirty && !isValid}
        >
          Submit My Application
        </Button>
        <Grid item xs={4} />
      </Grid>
      <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert severity={severity} onClose={handleClose}>
          {message}
        </Alert>
      </Snackbar>
    </Grid>
  );
};

export default ReviewAndSubmit;
