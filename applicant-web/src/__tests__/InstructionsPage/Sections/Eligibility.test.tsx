import React from 'react';
import { render } from '@testing-library/react';
import Eligibility from '../../../components/InstructionPage/Sections/Eligibility';

describe('eligibility card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<Eligibility />);

    const text =
      'If you are in the United States and seek an Advance Parole Document, you may apply if:';

    expect(getByText(text)).toBeInTheDocument();
  });
});
