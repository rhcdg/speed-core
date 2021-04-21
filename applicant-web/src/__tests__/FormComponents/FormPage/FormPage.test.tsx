import React from 'react';
import {
  cleanup,
  fireEvent,
  render,
  screen,
  waitFor
} from '@testing-library/react';
import { Formik } from 'formik';
import axios from 'axios';
import FormPage from '../../../components/FormPage/FormPage';
import { formTestData } from '../../../utils/formUtils';

jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

describe('landing page', () => {
  afterEach(cleanup);

  it('renders without crashing', async () => {
    render(
      <Formik initialValues={{}} onSubmit={jest.fn()}>
        <FormPage />
      </Formik>
    );

    expect(screen.getByText('Next')).toBeInTheDocument();
  });
  jest.setTimeout(20000);

  it('clicks next', async () => {
    jest.setTimeout(20000);

    const responseObj = {
      data: {
        status: 'SUCCESS',
        address: {
          firmName: null,
          address1: null,
          address2: null,
          address2Abbreviation: null,
          city: null,
          cityAbbreviation: null,
          state: null,
          urbanization: null,
          zip5: null,
          zip4: null,
          deliveryPoint: null,
          carrierRoute: null,
          footnotes: null,
          dpvConfirmation: null,
          dpvcmra: null,
          dpvFootnotes: null,
          business: null,
          centralDeliveryPoint: null,
          vacant: null
        },
        error: null,
        id: 2
      }
    };
    maxios.post.mockResolvedValue(responseObj);
    maxios.patch.mockResolvedValue(formTestData);
    const { getByText } = render(
      <Formik initialValues={{ supportDocument: [] }} onSubmit={jest.fn()}>
        <FormPage />
      </Formik>
    );

    const button = getByText('Next');

    await waitFor(() => fireEvent.click(button));
    await waitFor(() => {
      expect(
        getByText(
          'Please explain how you qualify for an Advance Parole Document, and what circumstances warrant issuance of Advance Parole.'
        )
      ).toBeInTheDocument();
    });

    await waitFor(() => fireEvent.click(button));
    await waitFor(() => {
      expect(
        getByText(
          'Please include all required documents as listed in the Requirements, Initial Evidence portion of the Instructions.'
        )
      ).toBeInTheDocument();
    });

    await waitFor(() => fireEvent.click(button));
    await waitFor(() => {
      expect(getByText('Signature of Applicant')).toBeInTheDocument();
    });
  });
});
