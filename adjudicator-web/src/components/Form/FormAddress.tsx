import React from 'react';
import { Grid } from '@material-ui/core';
import { CaseDetailPanelProps } from '../../utils/caseUtils';
import FormInput from '../Form/FormInput';

const FormAddress: React.FC<CaseDetailPanelProps> = ({
  kase,
  addressType = 'home'
}) => {
  return (
    <>
      <Grid container className="form-row">
        <FormInput
          label="Address"
          id="address"
          size={4}
          value={
            addressType === 'home'
              ? kase?.form?.address
              : kase?.form?.deliveryAddress
          }
        />
        <FormInput
          label="Address 2"
          id="address2"
          size={4}
          value={
            addressType === 'home'
              ? kase?.form?.address2
              : kase?.form?.deliveryAddress2
          }
        />
      </Grid>

      <Grid container className="form-row">
        <FormInput
          label="City"
          id="city"
          size={4}
          value={
            addressType === 'home' ? kase?.form?.city : kase?.form?.deliveryCity
          }
        />
        <FormInput
          label="State"
          id="state"
          size={4}
          value={
            addressType === 'home'
              ? kase?.form?.state
              : kase?.form?.deliveryState
          }
        />
        <FormInput
          label="Zip Code"
          id="zipCode"
          size={4}
          value={
            addressType === 'home'
              ? kase?.form?.zipCode
              : kase?.form?.deliveryZipCode
          }
        />
      </Grid>

      <Grid container className="form-row">
        <FormInput
          label="Postal Code"
          id="postalCode"
          size={4}
          value={
            addressType === 'home'
              ? kase?.form?.postalCode
              : kase?.form?.deliveryPostalCode
          }
        />
        <FormInput
          label="Province"
          id="province"
          size={4}
          value={
            addressType === 'home'
              ? kase?.form?.province
              : kase?.form?.deliveryProvince
          }
        />
        <FormInput
          label="Country"
          id="country"
          size={4}
          value={
            addressType === 'home'
              ? kase?.form?.country
              : kase?.form?.deliveryCountry
          }
        />
      </Grid>
    </>
  );
};

export default FormAddress;
