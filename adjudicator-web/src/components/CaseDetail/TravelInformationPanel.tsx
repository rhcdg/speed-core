import React from 'react';
import { Grid } from '@material-ui/core';
import FormInput from '../Form/FormInput';
import FormAddress from '../Form/FormAddress';
import { CaseDetailPanelProps } from '../../utils/caseUtils';

const TravelInformationPanel: React.FC<CaseDetailPanelProps> = ({ kase }) => {
  return (
    <Grid container>
      <Grid container className="form-row">
        <Grid item lg={12}>
          <label htmlFor="purposeOfTrip">Purpose Of Trip</label>
          <textarea
            rows={5}
            className="text-area"
            id="purposeOfTrip"
            name="purposeOfTrip"
            value={kase?.form?.purposeOfTrip ? kase?.form?.purposeOfTrip : ''}
            disabled
          />
        </Grid>
      </Grid>

      <Grid container className="form-row">
        <FormInput
          label="Countries Intending to Visit"
          id="countriesVisiting"
          size={12}
          value={kase?.form?.countriesVisiting}
        />
      </Grid>

      <Grid container className="form-row">
        <FormInput label="Date of Departure" id="dateOfDeparture" size={4} />
        <FormInput
          label="Length of Trip (in days)"
          id="lengthOfTrip"
          size={4}
          value={kase?.form?.lengthOfTrip}
        />
      </Grid>

      <Grid container className="form-row">
        <label className="checkbox">
          <input
            type="checkbox"
            name="inProceedings"
            data-testid="inProceedings"
            id="inProceedings"
            checked={kase?.form?.inProceedings}
            disabled
          />
          Currently in Exclusion, Deportation, Removal or Rescission
          Proceedings?
        </label>
      </Grid>

      <Grid container className="form-row">
        <label className="checkbox">
          <input
            type="checkbox"
            name="previouslyIssued"
            data-testid="previouslyIssued"
            id="previouslyIssued"
            checked={kase?.form?.previouslyIssued}
            disabled
          />
          Previously Issued a Reentry Permit or Refugee Travel Document?
        </label>
      </Grid>

      <hr />

      <Grid container className="form-row">
        Requested Documentation Delivery Address:
      </Grid>
      <FormAddress kase={kase} addressType="delivery" />
    </Grid>
  );
};

export default TravelInformationPanel;
