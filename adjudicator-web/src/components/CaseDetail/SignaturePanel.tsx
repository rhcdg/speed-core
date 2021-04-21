import React from 'react';
import { Grid } from '@material-ui/core';
import FormInput from '../Form/FormInput';
import { CaseDetailPanelProps } from '../../utils/caseUtils';

import './case-detail.scss';

const SignaturePanel: React.FC<CaseDetailPanelProps> = ({ kase }) => {
  return (
    <Grid container>
      <Grid container className="form-row">
        <FormInput
          label="Signature of Applicant"
          id="signature"
          size={6}
          value={kase?.form?.signature}
        />
        <FormInput
          label="Date"
          id="date"
          size={6}
          value={kase?.form?.signatureDate}
        />
      </Grid>

      <Grid container className="form-row">
        <FormInput
          label="Daytime Phone"
          id="daytimePhone"
          size={6}
          value={kase?.form?.phoneNumber}
        />
      </Grid>
    </Grid>
  );
};

export default SignaturePanel;
