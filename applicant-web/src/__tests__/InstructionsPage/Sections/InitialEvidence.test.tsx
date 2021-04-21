import React from 'react';
import { render } from '@testing-library/react';
import InitialEvidence from '../../../components/InstructionPage/Sections/InitialEvidence';

describe('initial evidence card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<InitialEvidence />);

    const text =
      '(1) A copy of any document issued to you by USCIS showing your present status, if any, in the United States; and';

    expect(getByText(text)).toBeInTheDocument();
  });
});
