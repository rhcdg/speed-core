import React from 'react';
import { Grid, GridSize } from '@material-ui/core';

interface FormInputProps {
  label: string;
  id: string;
  size: GridSize;
  value?: string | number | null | undefined;
}

const FormInput: React.FC<FormInputProps> = ({ label, id, size, value }) => {
  return (
    <Grid item lg={size}>
      <label htmlFor={id}>{label}</label>
      {value && (
        <input
          className="text-input"
          id={id}
          name={id}
          disabled
          value={value}
        />
      )}
    </Grid>
  );
};

export default FormInput;
