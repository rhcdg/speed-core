import React from 'react';
import { render } from '@testing-library/react';
import { Formik } from 'formik';
import PersonalInformation from '../../../components/FormComponents/PersonalInformation/PersonalInformation';
import { I131ValidationSchema } from '../../../utils/formUtils';

const initialFormValues = I131ValidationSchema.cast({});

describe('appliacant info', () => {
  test('it renders without crashing', async () => {
    const applicantInfo = render(
      <Formik initialValues={initialFormValues} onSubmit={jest.fn()}>
        <PersonalInformation />
      </Formik>
    );

    expect(applicantInfo).not.toBeUndefined();
  });
});
