import React from 'react';
import { render } from '@testing-library/react';
import TravelWarning from '../../../components/InstructionPage/Sections/TravelWarning';

describe('travel warning card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<TravelWarning />);

    const text =
      'Before you apply for an Advance Parole Document, read the following travel warning carefully.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
