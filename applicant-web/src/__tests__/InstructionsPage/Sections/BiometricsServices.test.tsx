import React from 'react';
import { render } from '@testing-library/react';
import BiometricsServices from '../../../components/InstructionPage/Sections/BiometricsServices';

describe('biometrics services card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<BiometricsServices />);

    const text =
      'Biometric services are not required for individuals who are applying for Advanced Parole travel documents from within the United States.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
