import React, { ChangeEvent } from 'react';
import {
  Box,
  FormControl,
  MenuItem,
  Select,
  FormLabel,
  FormControlLabel,
  Radio,
  RadioGroup,
  Link,
  Container,
  makeStyles,
  Grid,
  Checkbox
} from '@material-ui/core';
import { useFormikContext } from 'formik';
import TextField from '../../TextField/TextField';
import {
  countries,
  Country,
  I131FormValues,
  SharedProps,
  State,
  states,
  VisaClass,
  visaClasses
} from '../../../utils/formUtils';
import InfoCard from '../../InfoCard/InfoCard';

const useStyles = makeStyles({
  infoCardContainer: {
    maxWidth: 'none',
    padding: '0px 0px 24px 0px'
  },
  required: {
    color: 'red'
  },
  rowContainer: {
    marginBottom: '2em'
  },
  selectContainer: {
    width: '100%',
    padding: '0px 8px',
    marginTop: '16px'
  },
  radioContainer: {
    margin: '16px 0px 0px 16px'
  },
  checkboxContainer: {
    margin: '36px 0px 0px 16px'
  }
});

const createStateOption = (state: State, i: number) => (
  <MenuItem key={i} value={state.abbreviation}>
    {state.name}
  </MenuItem>
);

const createCountryOption = (country: Country) => (
  <MenuItem key={country.code} value={country.code}>
    {country.name}
  </MenuItem>
);

const createVisaClassOption = (visaClass: VisaClass) => (
  <MenuItem key={visaClass.type} value={visaClass.type}>
    {visaClass.description}
  </MenuItem>
);

const infoCardContents = (
  <div>
    If you prefer to download a paper version of this application please visit{' '}
    <Link href="www.uscis.gov/i-131">www.uscis.gov/i-131</Link>
  </div>
);

const PersonalInformation: React.FC<SharedProps> = ({ readOnly }) => {
  const classes = useStyles();
  const { values, setFieldValue } = useFormikContext<I131FormValues>();
  const [checked, setChecked] = React.useState<boolean>(false);

  const stateOptions = states.map((state: State, i: number) =>
    createStateOption(state, i)
  );
  const countryOptions = countries.map((country: Country) =>
    createCountryOption(country)
  );

  const visaClassOptions = visaClasses.map((visaClass: VisaClass) =>
    createVisaClassOption(visaClass)
  );

  const createSelectOnChange = (fieldName: string) => (
    event: ChangeEvent<{ name?: string; value: unknown }>
  ) => {
    setFieldValue(fieldName, event.target.value);
  };

  const handleRadioChangeGender = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setFieldValue('gender', (event.target as HTMLInputElement).value);
  };

  const checkChange = () => {
    setChecked(!checked);
  };

  return (
    <Box>
      <Container className={classes.infoCardContainer}>
        {!readOnly && <InfoCard contents={infoCardContents} parent="form" />}
      </Container>
      <Grid container className={classes.rowContainer}>
        <Grid item md={4}>
          <TextField
            name="anumber"
            label="Alien Registration Number (A-Number)"
            value={values.anumber}
            req
          />
        </Grid>
        <Grid item md={4}>
          <TextField
            name="dateOfBirth"
            label="Date of Birth"
            value={values.dateOfBirth}
            placeholder="MM/DD/YYYY"
            req
          />
        </Grid>
        <Grid item md={4} />
      </Grid>
      <Grid container className={classes.rowContainer}>
        <Grid item md={4}>
          <TextField
            name="lastName"
            label="Family Name (Last Name)"
            value={values.lastName}
            req
          />
        </Grid>
        <Grid item md={4}>
          <TextField
            name="firstName"
            label="Given Name (First Name)"
            value={values.firstName}
            req
            fullWidth
          />
        </Grid>
        <Grid item md={4}>
          <TextField
            name="middleName"
            label="Middle Name"
            value={values.middleName}
          />
        </Grid>
      </Grid>
      <Grid container>
        <Grid item md={6}>
          <TextField
            name="inCareOf"
            label="In Care Of"
            value={values.inCareOf}
          />
        </Grid>
        <Grid item md={6}>
          <FormControlLabel
            control={
              <Checkbox
                checked={checked}
                onChange={checkChange}
                name="alternateAddress"
              />
            }
            label="Send all of my documents to this address"
            className={classes.checkboxContainer}
          />
        </Grid>
      </Grid>
      <Grid container>
        <Grid item md={6}>
          <TextField
            name="address"
            label="Mailing Address 1"
            value={values.address}
            req
          />
        </Grid>
        <Grid item md={6}>
          <TextField
            name="address2"
            label="Mailing Address 2"
            value={values.address2}
          />
        </Grid>
      </Grid>
      <Grid container>
        <Grid item md={6}>
          <TextField name="city" label="City" value={values.city} req />
        </Grid>
        <Grid item md={3}>
          <FormControl className={classes.selectContainer}>
            <label htmlFor="state">
              State <span className={classes.required}>*</span>
            </label>
            <Select
              variant="outlined"
              name="state"
              data-testid="state"
              labelId="state-input-label"
              onChange={createSelectOnChange('state')}
              value={values.state || ''}
            >
              {stateOptions}
            </Select>
          </FormControl>
        </Grid>
        <Grid item md={3}>
          <TextField
            name="zipCode"
            label="Zip Code"
            value={values.zipCode}
            req
          />
        </Grid>
      </Grid>
      <Grid container>
        <Grid item md={3}>
          <TextField
            name="postcalCode"
            label="Postal Code"
            value={values.postalCode}
          />
        </Grid>
        <Grid item md={6}>
          <TextField name="province" label="Province" value={values.province} />
        </Grid>
        <Grid item md={3}>
          <FormControl className={classes.selectContainer}>
            <label htmlFor="country">
              Country <span className={classes.required}>*</span>
            </label>
            <Select
              variant="outlined"
              name="country"
              data-testid="country"
              labelId="country-input-label"
              onChange={createSelectOnChange('country')}
              value={values.country || ''}
            >
              {countryOptions}
            </Select>
          </FormControl>
        </Grid>
      </Grid>
      <Grid container>
        <Grid item md={3}>
          <FormControl className={classes.selectContainer}>
            <label htmlFor="countryOfBirth">
              Country of Birth <span className={classes.required}>*</span>
            </label>
            <Select
              variant="outlined"
              name="countryOfBirth"
              data-testid="countryOfBirth"
              labelId="country-of-birth-input-label"
              onChange={createSelectOnChange('countryOfBirth')}
              value={values.countryOfBirth || ''}
            >
              {countryOptions}
            </Select>
          </FormControl>
        </Grid>
        <Grid item md={3}>
          <FormControl className={classes.selectContainer}>
            <label htmlFor="countryOfCitizenship">Country of Citizenship</label>
            <Select
              variant="outlined"
              name="countryOfCitizenship"
              data-testid="countryOfCitizenship"
              labelId="country-of-citizenship-input-label"
              onChange={createSelectOnChange('countryOfCitizenship')}
              value={values.countryOfCitizenship || ''}
            >
              {countryOptions}
            </Select>
          </FormControl>
        </Grid>
        <Grid item md={3}>
          <FormControl className={classes.selectContainer}>
            <label htmlFor="visaClass">
              Visa Class <span className={classes.required}>*</span>
            </label>
            <Select
              variant="outlined"
              name="visaClass"
              data-testid="visaClass"
              labelId="visa-class-input-label"
              onChange={createSelectOnChange('visaClass')}
              value={values.visaClass || ''}
            >
              {visaClassOptions}
            </Select>
          </FormControl>
        </Grid>
      </Grid>
      <Grid container>
        <Grid item md={3}>
          <FormControl component="fieldset" className={classes.radioContainer}>
            <FormLabel component="legend">
              Gender <span className={classes.required}>*</span>
            </FormLabel>
            <RadioGroup
              aria-label="gender"
              name="gender"
              value={values.gender || ''}
              onChange={handleRadioChangeGender}
              row
            >
              <FormControlLabel
                value="FEMALE"
                control={<Radio />}
                label="Female"
              />
              <FormControlLabel value="MALE" control={<Radio />} label="Male" />
            </RadioGroup>
          </FormControl>
        </Grid>
        <Grid item md={3}>
          <TextField
            name="ssn"
            label="U.S. Social Security Number"
            value={values.ssn}
          />
        </Grid>
      </Grid>
      <FormControl component="fieldset" className={classes.radioContainer}>
        <RadioGroup
          aria-label="own-submission"
          name="ownSubmission"
          value={values.ownSubmission || ''}
          row
        >
          <FormControlLabel
            value="true"
            control={<Radio />}
            label="I am preparing my own application"
          />
          <FormControlLabel
            value="false"
            control={<Radio />}
            disabled
            label="Someone is preparing this application for me"
          />
        </RadioGroup>
      </FormControl>
    </Box>
  );
};

export default PersonalInformation;
