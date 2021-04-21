import React, { Dispatch, SetStateAction } from 'react';
import { Button, Grid, makeStyles, Theme } from '@material-ui/core';
import { useFormikContext } from 'formik';
import Header from '../Header/Header';
import FormBase from '../FormBase/FormBase';
import { I131FormValues, I131ValidationSchema } from '../../utils/formUtils';
import PersonalInformation from '../FormComponents/PersonalInformation/PersonalInformation';
import PreparerInformation from '../FormComponents/PreparerInformation/PreparerInformation';
import SharedButtons from '../SharedButtons/SharedButtons';
import SupportingEvidence from '../FormComponents/SupportingEvidence/SupportingEvidence';
import TravelInformation from '../FormComponents/TravelInformation/TravelInformation';
import ReviewAndSubmit from '../FormComponents/ReviewAndSubmit/ReviewAndSubmit';
import Signature from '../FormComponents/Signature/Signature';
import { createForm, patchForm } from '../../utils/formService';

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    width: '100%'
  },
  stepper: {
    width: '100%',
    height: '50px',
    padding: theme.spacing(1)
  },
  stepperHeader: {
    padding: theme.spacing(1)
  },
  buttonGroup: {
    marginTop: theme.spacing(2)
  },
  button: {
    padding: theme.spacing(1)
  }
}));

const getFormContent = (index: number, form?: I131FormValues) => {
  switch (index) {
    case 0:
      return <PersonalInformation />;
    case 1:
      return <PreparerInformation />;
    case 2:
      return <TravelInformation />;
    case 3:
      return <SupportingEvidence />;
    case 4:
      return <Signature />;
    case 5:
      return <ReviewAndSubmit form={form} />;
    default:
      return <div />;
  }
};

interface NextButtonProps {
  activeStep: number;
  setActiveStep: Dispatch<SetStateAction<number>>;
  setForm: Dispatch<SetStateAction<I131FormValues | undefined>>;
}

const NextButton: React.FC<NextButtonProps> = ({
  activeStep,
  setActiveStep,
  setForm
}) => {
  const classes = useStyles();
  const {
    values,
    setFieldValue,
    validateForm
  } = useFormikContext<I131FormValues>();

  const handleNext = async () => {
    if (validateForm()) {
      if (activeStep === 0) {
        await createForm(values)
          .then(resp => {
            setFieldValue('id', resp.id);
            setActiveStep(2);
          })
          .catch(err => {
            throw new Error(err);
          });
      } else {
        await patchForm(values)
          .then(resp => {
            setForm(resp);
            setActiveStep(activeStep + 1);
          })
          .catch(err => {
            throw new Error(err);
          });
      }
    }
  };

  return (
    <Grid container direction="row" justify="flex-end">
      <Grid className={classes.buttonGroup} item>
        <Button
          variant="contained"
          color="primary"
          onClick={handleNext}
          className={classes.button}
        >
          Next
        </Button>
      </Grid>
    </Grid>
  );
};

const FormPage: React.FC = () => {
  const [activeStep, setActiveStep] = React.useState(0);
  const initialFormValues = I131ValidationSchema.cast({
    applicationType: 'ONE_D',
    supportDocument: []
  });
  const [form, setForm] = React.useState<I131FormValues | undefined>();

  return (
    <Grid container id="main-content" className="main-content">
      <SharedButtons page="form" />
      <Header />
      <Grid item xs={12}>
        <FormBase
          initialValues={initialFormValues}
          onSubmit={async (values, { setSubmitting }) => {
            setSubmitting(true);
            // try {
            //   setSubmitting(true);
            //   // await formService.submitForm(values);
            // } catch (error) {
            //   throw new Error(error);
            // } finally {
            //   setSubmitting(false);
            // }
          }}
          validationSchema={I131ValidationSchema}
        >
          {getFormContent(activeStep, form)}
          {activeStep !== 5 && (
            <NextButton
              activeStep={activeStep}
              setActiveStep={setActiveStep}
              setForm={setForm}
            />
          )}
        </FormBase>
      </Grid>
    </Grid>
  );
};

export default FormPage;
