import React from 'react';
import { render } from '@testing-library/react';
import GeneralInstructions from '../../../components/InstructionPage/Sections/GeneralInstructions';

describe('general instructions card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<GeneralInstructions />);

    const text =
      'If you are completing this form on a computer, the data you enter will be captured using 2D barcode technology. This capture will ensure that the data you provide is accurately entered into USCIS systems. As you complete each field, the 2D barcode field at the bottom of each page will shift as data is captured. Upon receipt of your form, USCIS will use the 2D barcode to extract the data from the form. Please do not damage the 2D barcode (puncture, staple, spill on, write on, etc.) as this could affect the ability of USCIS to timely process your form.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
