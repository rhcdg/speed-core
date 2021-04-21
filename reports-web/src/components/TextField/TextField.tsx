import React from 'react';
import MuiTextField, {
  TextFieldProps as MuiTextFieldProps
} from '@material-ui/core/TextField';
import { useField } from 'formik';

type TextFieldProps = Omit<MuiTextFieldProps, 'name'> & { name: string };

const TextField: React.FC<TextFieldProps> = ({
  name,
  ...props
}: TextFieldProps) => {
  const [field, { touched, error }] = useField<string>(name);

  const showError = touched && Boolean(error);

  return (
    <MuiTextField
      {...props}
      {...field}
      variant="standard"
      error={showError}
      helperText={showError && error}
    />
  );
};

export default TextField;
