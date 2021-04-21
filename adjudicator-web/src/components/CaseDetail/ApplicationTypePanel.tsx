import React from 'react';
import { Grid } from '@material-ui/core';
import FormInput from '../Form/FormInput';
import { CaseDetailPanelProps } from '../../utils/caseUtils';

const ApplicationTypePanel: React.FC<CaseDetailPanelProps> = ({ kase }) => {
  return (
    <Grid container>
      <Grid container className="form-row">
        <FormInput
          label="Application Type"
          id="applicationType"
          size={6}
          value={kase?.form?.applicationType}
        />
        <FormInput
          label="Number Of Trips"
          id="numberOfTrips"
          size={6}
          value={kase?.form?.numberOfTrips}
        />
      </Grid>

      <Grid container className="form-row">
        <Grid item lg={12}>
          <label htmlFor="explanationOfEligibility">
            Explanation of Eligibility
          </label>
          <textarea
            rows={10}
            className="text-area"
            id="explanationOfEligibility"
            name="explanationOfEligibility"
            value={
              kase?.form?.explanationOfEligibility
                ? kase?.form?.explanationOfEligibility
                : ''
            }
            disabled
          />
        </Grid>
      </Grid>
    </Grid>
  );
};

export default ApplicationTypePanel;
