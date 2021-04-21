import React from 'react';
import { render } from '@testing-library/react';
import ProcessingInformation from '../../../components/InstructionPage/Sections/ProcessingInformation';

describe('processing information card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<ProcessingInformation />);

    const text =
      'Requests for More Information, Including Biometrics, or Interview';

    expect(getByText(text)).toBeInTheDocument();
  });
});
