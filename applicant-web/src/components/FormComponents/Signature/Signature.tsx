import React from 'react';
import { Box, Grid } from '@material-ui/core';
import { useFormikContext } from 'formik';
import { I131FormValues, SharedProps } from '../../../utils/formUtils';
import InfoCard from '../../InfoCard/InfoCard';
import TextField from '../../TextField/TextField';

const cardContents =
  'Read the information on penalties in the Instruction portion of this form before completing this Part.';

const Signature: React.FC<SharedProps> = ({ readOnly }) => {
  const { values } = useFormikContext<I131FormValues>();
  return (
    <Box>
      {!readOnly && <InfoCard contents={cardContents} parent="form" />}
      <Grid container>
        <Grid item md={6}>
          <TextField
            name="signature"
            value={values.signature}
            label="Signature of Applicant"
            req
          />
        </Grid>
        <Grid item md={3}>
          <TextField
            name="signatureDate"
            value={values.signatureDate}
            label="Date"
            req
            placeholder="MM/DD/YYYY"
          />
        </Grid>
        <Grid item md={3}>
          <TextField
            name="phoneNumber"
            value={values.phoneNumber}
            label="Applicant's Daytime Phone #"
            placeholder="XXX-XXX-XXXX"
          />
        </Grid>
      </Grid>
    </Box>
  );
};

export default Signature;
