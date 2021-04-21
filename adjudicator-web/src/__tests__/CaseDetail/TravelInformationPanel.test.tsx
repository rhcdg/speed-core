import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import TravelInformationPanel from '../../components/CaseDetail/TravelInformationPanel';
import { kase } from '../utils/caseData';

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    render(<TravelInformationPanel kase={kase} />);
    const input = screen.getByLabelText('Postal Code') as HTMLInputElement;
    expect(input.value).toBe('deliveryPostalCode4');
  });

  it('renders empty string for purpose of trip', () => {
    delete kase?.form?.purposeOfTrip;
    render(<TravelInformationPanel kase={kase} />);
    const input = screen.getByLabelText('Purpose Of Trip') as HTMLInputElement;
    expect(input.value).toBe('');
  });
});
