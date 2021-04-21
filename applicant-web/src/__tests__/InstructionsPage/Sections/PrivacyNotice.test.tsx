import React from 'react';
import { render } from '@testing-library/react';
import PrivacyNotice from '../../../components/InstructionPage/Sections/PrivacyNotice';

describe('privacy notice card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<PrivacyNotice />);

    const text = 'Do not mail your completed Form I-131 to this address.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
