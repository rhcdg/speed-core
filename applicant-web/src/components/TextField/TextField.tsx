import React from 'react';
import MuiTextField, {
  TextFieldProps as MuiTextFieldProps
} from '@material-ui/core/TextField';
import { useField } from 'formik';
import { FormControl, makeStyles } from '@material-ui/core';

const useStyles = makeStyles({
  req: {
    color: 'red'
  },
  container: {
    width: '100%',
    padding: '16px'
  }
});

type TextFieldProps = Omit<MuiTextFieldProps, 'name'> & {
  name: string;
  req?: boolean;
};

const TextField: React.FC<TextFieldProps> = ({
  name,
  label,
  req = false,
  ...props
}: TextFieldProps) => {
  const classes = useStyles();
  const [field, { touched, error }] = useField<string>(name);

  const showError = touched && Boolean(error);

  return (
    <FormControl className={classes.container}>
      <label htmlFor={name}>
        {label} <span className={classes.req}>{req ? '*' : null}</span>
      </label>
      <MuiTextField
        {...props}
        {...field}
        id={name}
        variant="outlined"
        error={showError}
        helperText={showError && error}
      />
    </FormControl>
  );
};

TextField.defaultProps = {
  req: false
};

export default TextField;
