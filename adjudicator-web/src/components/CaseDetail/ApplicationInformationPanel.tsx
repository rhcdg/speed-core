import React from 'react';
import { Grid } from '@material-ui/core';
import FormInput from '../Form/FormInput';
import FormAddress from '../Form/FormAddress';
import { CaseDetailPanelProps } from '../../utils/caseUtils';

const ApplicationInformationPanel: React.FC<CaseDetailPanelProps> = ({
  kase
}) => {
  return (
    <Grid container>
      <Grid container className="form-row">
        <FormInput
          label="Family Name/Last Name"
          id="familyName"
          size={4}
          value={kase?.form?.lastName}
        />
        <FormInput
          label="Given Name/First Name"
          id="givenName"
          size={4}
          value={kase?.form?.firstName}
        />
        <FormInput
          label="Middle Name"
          id="middleName"
          size={4}
          value={kase?.form?.middleName}
        />
      </Grid>

      <Grid container className="form-row">
        <FormInput
          label="In Care Of"
          id="inCareOf"
          size={4}
          value={kase?.form?.inCareOf}
        />
      </Grid>

      <hr />

      <FormAddress kase={kase} />

      <hr />

      <Grid container className="form-row">
        <FormInput
          label="A-Number"
          id="aNumber"
          size={4}
          value={kase?.form?.anumber}
        />
      </Grid>

      <Grid container className="form-row">
        <FormInput
          label="Country of Birth"
          id="countryOfBirth"
          size={4}
          value={kase?.form?.countryOfBirth}
        />
        <FormInput
          label="Country of Citizenship"
          id="countryOfCitizenship"
          size={4}
          value={kase?.form?.countryOfCitizenship}
        />
        <FormInput
          label="Visa Class"
          id="visaClass"
          size={4}
          value={kase?.form?.visaClass}
        />
      </Grid>

      <Grid container className="form-row">
        <FormInput
          label="Date of Birth"
          id="dateOfBirth"
          size={4}
          value={kase?.form?.dateOfBirth}
        />
        <FormInput
          label="Gender"
          id="gender"
          size={4}
          value={kase?.form?.gender}
        />
        {/* <FormInput label="SS#" id="ss" size={4} /> */}
      </Grid>
    </Grid>
  );
};

export default ApplicationInformationPanel;
