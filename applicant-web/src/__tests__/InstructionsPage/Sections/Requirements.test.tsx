import React from 'react';
import { render } from '@testing-library/react';
import Requirements from '../../../components/InstructionPage/Sections/Requirements';

describe('requirements card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<Requirements />);

    const text =
      'NOTE: Form I-94 Arrival-Departure Record is not acceptable as a photo identity document.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
