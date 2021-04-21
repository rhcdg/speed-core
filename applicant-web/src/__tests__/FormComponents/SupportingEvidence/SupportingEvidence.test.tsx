import React from 'react';
import { render } from '@testing-library/react';
import { Formik } from 'formik';
import SupportingEvidence from '../../../components/FormComponents/SupportingEvidence/SupportingEvidence';
import { I131ValidationSchema } from '../../../utils/formUtils';

const initialFormValues = I131ValidationSchema.cast({});

describe('processing info', () => {
  test('it renders without crashing', async () => {
    const applicantInfo = render(
      <Formik initialValues={initialFormValues} onSubmit={jest.fn()}>
        <SupportingEvidence />
      </Formik>
    );

    expect(applicantInfo).not.toBeUndefined();
  });
});
