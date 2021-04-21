import React from 'react';
import { render } from '@testing-library/react';
import Decision from '../../../components/InstructionPage/Sections/Decision';

describe('decision card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<Decision />);

    const text =
      'The decision on Form I-131 involves a determination of whether you have established eligibility for the requested document. You will be notified of the decision in writing.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
