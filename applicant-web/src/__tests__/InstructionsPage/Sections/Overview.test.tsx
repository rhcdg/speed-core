import React from 'react';
import { render } from '@testing-library/react';
import Overview from '../../../components/InstructionPage/Sections/Overview';

describe('overview card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<Overview />);

    const text =
      'Parole allows an alien to physically enter into the United States for a specific purpose. An individual who has been “paroled” has not been admitted to the United States and remains an “applicant for admission” even while paroled.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
