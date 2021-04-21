import React from 'react';
import { render } from '@testing-library/react';
import Photographs from '../../../components/InstructionPage/Sections/Photographs';

describe('photographs card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<Photographs />);

    const text =
      'NOTE: Because of the current USCIS scanning process, if a digital photo is submitted, it must be produced from a high-resolution camera that has at least 3.5 mega pixels of resolution.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
