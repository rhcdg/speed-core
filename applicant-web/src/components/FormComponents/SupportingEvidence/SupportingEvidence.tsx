import React from 'react';
import { DropzoneArea } from 'material-ui-dropzone';
import { Grid, Typography, makeStyles } from '@material-ui/core';
import { useFormikContext } from 'formik';
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
  }
});
const SupportingEvidence: React.FC<SharedProps> = ({ readOnly }) => {
  const classes = useStyles();
  const { setFieldValue } = useFormikContext<I131FormValues>();
  const handleChangeDropzone = (files: File[]) => {
    const newFiles: SupportDocument[] = files.map(
      (file, index): SupportDocument => ({
        id: String(index),
        formType: 'I-131',
        name: file.name,
        status: 'Submitted',
        url: 'placeholderURL'
      })
    );
    setFieldValue('supportDocument', newFiles);
  };
  return (
    <Grid container>
      {!readOnly && (
        <Grid item xs={12}>
          <Typography variant="body1">
            Please include all required documents as listed in the Requirements,
            Initial Evidence portion of the Instructions.
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
      <Grid item xs={12} />
    </Grid>
  );
};

export default SupportingEvidence;
