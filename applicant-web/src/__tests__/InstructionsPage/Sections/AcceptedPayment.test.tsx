import React from 'react';
import { render } from '@testing-library/react';
import AcceptedPayment from '../../../components/InstructionPage/Sections/AcceptedPayment';

describe('accepted payment card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<AcceptedPayment />);

    const text =
      'Use the following guidelines when you prepare your checks or money orders for the Form I-131 filing fee and biometric services fee:';

    expect(getByText(text)).toBeInTheDocument();
  });
});
