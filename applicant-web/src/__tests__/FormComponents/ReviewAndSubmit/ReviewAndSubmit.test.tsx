import React from 'react';
import { fireEvent, render, waitFor } from '@testing-library/react';
import { Formik } from 'formik';
import axios from 'axios';
import ReviewAndSubmit from '../../../components/FormComponents/ReviewAndSubmit/ReviewAndSubmit';
import { formTestData, I131ValidationSchema } from '../../../utils/formUtils';

jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

const initialFormValues = I131ValidationSchema.cast({});

describe('review and submit', () => {
  test('it renders without crashing', async () => {
    const applicantInfo = render(
      <Formik initialValues={initialFormValues} onSubmit={jest.fn()}>
        <ReviewAndSubmit />
      </Formik>
    );

    expect(applicantInfo).not.toBeUndefined();
  });

  test('it tests submit onClick', async () => {
    const form = formTestData;
    maxios.put.mockResolvedValue(form);

    const { getByText } = render(
      <Formik initialValues={initialFormValues} onSubmit={jest.fn()}>
        <ReviewAndSubmit form={form} />
      </Formik>
    );

    const button = getByText('Submit My Application');
    fireEvent.click(button);

    await waitFor(() => expect(maxios.put).toHaveBeenCalled());
  });
});
