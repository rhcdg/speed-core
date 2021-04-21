import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import ApplicationTypePanel from '../../components/CaseDetail/ApplicationTypePanel';
import { kase } from '../utils/caseData';

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    render(<ApplicationTypePanel kase={kase} />);
    const input = screen.getByLabelText('Number Of Trips') as HTMLInputElement;
    expect(input.value).toBe('numberOfTrips4');
  });

  it('renders empty string for eligibility explaination', () => {
    delete kase?.form?.explanationOfEligibility;
    render(<ApplicationTypePanel kase={kase} />);
    const input = screen.getByLabelText(
      'Explanation of Eligibility'
    ) as HTMLInputElement;
    expect(input.value).toBe('');
  });
});
