import React from 'react';
import { render } from '@testing-library/react';
import FilingFees from '../../../components/InstructionPage/Sections/FilingFees';

describe('filing fees card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<FilingFees />);

    const text =
      'DO NOT MAIL CASH. You must submit all fees in the exact amounts.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
