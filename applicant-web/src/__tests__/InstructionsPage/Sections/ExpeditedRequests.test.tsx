import React from 'react';
import { render } from '@testing-library/react';
import ExpeditedRequest from '../../../components/InstructionPage/Sections/ExpeditedRequest';

describe('expedited request card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<ExpeditedRequest />);

    const text =
      'To request expedited processing of an application for a Reentry Permit, a Refugee Travel Document, or an Advance Parole Document for an individual outside the United States, other than under one of the Family Reunification Parole policies, type or print the word EXPEDITE in the top right corner of the application in black ink. USCIS recommends that you provide e-mail addresses and a fax number with any expedite request for a Reentry Permit, Refugee Travel Document, or Advance Parole Document.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
