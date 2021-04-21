import React from 'react';
import {
  FormControl,
  FormControlLabel,
  Grid,
  makeStyles,
  Radio,
  RadioGroup,
  Typography
} from '@material-ui/core';
import { useFormikContext } from 'formik';
import { DropzoneArea } from 'material-ui-dropzone';
import TextField from '../../TextField/TextField';
import {
  acceptedFiles,
  I131FormValues,
  SharedProps,
  SupportDocument
} from '../../../utils/formUtils';

const useStyles = makeStyles({
  previewChip: {
    minWidth: 160,
    maxWidth: 210
  },
  marginTop: {
    marginTop: '32px'
  }
});

const TravelInformation: React.FC<SharedProps> = ({ readOnly }) => {
  const classes = useStyles();
  const { values, setFieldValue } = useFormikContext<I131FormValues>();

  const handleChangeDropzone = (files: File[]) => {
    setFieldValue(
      'supportDocument',
      files.forEach(
        (file, index): SupportDocument => ({
          id: String(index),
          formType: 'I-131',
          name: file.name,
          status: 'Submitted',
          url: 'placeholderURL'
        })
      )
    );
  };

  const handleRadioChangeTrips = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setFieldValue('numberOfTrips', (event.target as HTMLInputElement).value);
  };
  const handleRadioChangeProceedings = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setFieldValue('inProceedings', (event.target as HTMLInputElement).value);
  };
  const handleRadioChangeIssued = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setFieldValue('previouslyIssued', (event.target as HTMLInputElement).value);
  };

  return (
    <Grid container>
      <Grid item xs={12}>
        <Typography variant="body1">
          Please explain how you qualify for an Advance Parole Document, and
          what circumstances warrant issuance of Advance Parole.
        </Typography>
        <TextField
          multiline
          rows={6}
          name="explanationOfEligibility"
          value={values.explanationOfEligibility}
        />
      </Grid>
      {!readOnly && (
        <Grid item xs={12}>
          <Typography variant="body1">
            Please include any documents you wish considered
          </Typography>
          <DropzoneArea
            onChange={handleChangeDropzone}
            filesLimit={10}
            acceptedFiles={acceptedFiles}
            maxFileSize={5000000}
            showPreviews
            showPreviewsInDropzone={false}
            useChipsForPreview
            previewGridProps={{ container: { spacing: 1, direction: 'row' } }}
            previewChipProps={{ classes: { root: classes.previewChip } }}
            previewText="Selected Files"
          />
        </Grid>
      )}
      <Grid item xs={12}>
        <Typography variant="body1">
          List the countries you intend to visit
        </Typography>
        <TextField
          multiline
          rows={2}
          name="countriesVisiting"
          value={values.countriesVisiting}
        />
      </Grid>
      <Grid item xs={12}>
        <Typography variant="body1">
          What is the purpose of your trip?
        </Typography>
        <TextField
          multiline
          rows={2}
          name="purposeOfTrip"
          value={values.purposeOfTrip}
        />
      </Grid>
      <Grid item container xs={12}>
        <Grid item xs={6}>
          <Typography variant="body1">
            How many trips do you intend to use this document?
          </Typography>
          <FormControl component="fieldset">
            <RadioGroup
              aria-label="number-of-trips"
              name="numberOfTrips"
              value={values.numberOfTrips}
              onChange={handleRadioChangeTrips}
            >
              <FormControlLabel
                value="one"
                control={<Radio />}
                label="One trip"
              />
              <FormControlLabel
                value="many"
                control={<Radio />}
                label="More than one trip"
              />
            </RadioGroup>
          </FormControl>
        </Grid>
        <Grid item xs={6}>
          <TextField
            name="dateOfDeparture"
            label="Intended Date of Departure"
            value={values.dateOfDeparture}
            placeholder="MM/DD/YYYY"
          />
          <Typography variant="body1">
            If you don&apos;t know, please list an approximate date.
          </Typography>
        </Grid>
      </Grid>
      <Grid item container xs={12} className={classes.marginTop}>
        <Grid item xs={6}>
          <Typography variant="body1">
            Are you now in exclusion, deportation, removal, or recession
            proceedings?
          </Typography>
          <FormControl component="fieldset">
            <RadioGroup
              row
              aria-label="in-proceedings"
              name="inProceedings"
              value={values.inProceedings}
              onChange={handleRadioChangeProceedings}
            >
              <FormControlLabel value="true" control={<Radio />} label="Yes" />
              <FormControlLabel value="false" control={<Radio />} label="No" />
            </RadioGroup>
          </FormControl>
          <TextField
            name="fieldControlOffice"
            label="If Yes, please list your DHS Office"
            value={values.fieldControlOffice}
          />
        </Grid>
        <Grid item xs={6}>
          <Typography variant="body1">
            Have you ever been issued a reentry permit or Refugee Travel
            Document?
          </Typography>
          <FormControl component="fieldset">
            <RadioGroup
              row
              aria-label="previously-issued"
              name="previouslyIssued"
              value={values.previouslyIssued}
              onChange={handleRadioChangeIssued}
            >
              <FormControlLabel value="true" control={<Radio />} label="Yes" />
              <FormControlLabel value="false" control={<Radio />} label="No" />
            </RadioGroup>
          </FormControl>
          <TextField
            name="dateFileOpen"
            label="If Yes, Date Issued"
            value={values.dateFileOpen}
            placeholder="MM/DD/YYYY"
          />
          <Typography variant="body1">
            Please use the date of the last document issued.
          </Typography>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default TravelInformation;
