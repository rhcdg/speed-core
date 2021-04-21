import React from 'react';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import TextField from '../../components/TextField/TextField';

const validationSchema = Yup.object({
  name: Yup.string().required('Required').default('')
}).defined();

const Wrapper: React.FC = () => (
  <Formik
    onSubmit={jest.fn()}
    initialValues={validationSchema.cast({})}
    validationSchema={validationSchema}
  >
    <TextField
      name="name"
      label="Name"
      id="name"
      inputProps={{ 'data-testid': 'name-input' }}
    />
  </Formik>
);

describe('<TextField />', () => {
  test('it renders without crashing', async () => {
    render(<Wrapper />);

    const input = await waitFor(() => screen.queryByText('Name'));

    expect(input).toBeInTheDocument();
  });

  test('an invalid input does not show its error until touched', async () => {
    render(<Wrapper />);

    const error = await waitFor(() => screen.queryByText('Required'));

    expect(error).not.toBeInTheDocument();
  });

  test('an invalid input shows its error once touched', async () => {
    render(<Wrapper />);

    const nameInput = await waitFor(() => screen.queryByTestId('name-input'));

    fireEvent.blur(nameInput as HTMLElement);

    await waitFor(() =>
      expect(screen.queryByText('Required')).toBeInTheDocument()
    );
  });
});
