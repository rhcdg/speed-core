import React from 'react';
import * as Yup from 'yup';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import Button from '@material-ui/core/Button';
import FormBase from '../../components/FormBase/FormBase';
import TextField from '../../components/TextField/TextField';

const sampleSchema = Yup.object({
  name: Yup.string().default('Adam'),
  favoriteColor: Yup.string().default('Blue')
});

const TestComponent: React.FC<{ onSubmit: jest.Mock }> = ({ onSubmit }) => (
  <FormBase
    validationSchema={sampleSchema}
    onSubmit={onSubmit}
    initialValues={sampleSchema.cast({})}
  >
    <TextField name="name" label="Name" />
    <TextField name="favoriteColor" label="Favorite color" />
    <Button type="submit" data-testid="submit">
      Submit
    </Button>
  </FormBase>
);

describe('<FormBase />', () => {
  let onSubmit: jest.Mock;

  beforeEach(() => {
    onSubmit = jest.fn();
  });

  test('it renders without crashing', async () => {
    const { container } = render(<TestComponent onSubmit={onSubmit} />);

    await waitFor(() => screen.queryByTestId('form-main'));

    expect(container).toBeDefined();
  });

  test('it calls onSubmit when on submit', async () => {
    render(<TestComponent onSubmit={onSubmit} />);

    const submit = await waitFor(() => screen.queryByTestId('submit'));

    fireEvent.submit(submit as HTMLElement);

    await waitFor(() => expect(onSubmit).toHaveBeenCalledTimes(1));
  });
});
