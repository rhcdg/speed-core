import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import ApplicationInformationPanel from '../../components/CaseDetail/ApplicationInformationPanel';
import { kase } from '../utils/caseData';

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    render(<ApplicationInformationPanel kase={kase} />);
    const input = screen.getByLabelText('Address') as HTMLInputElement;
    expect(input.value).toBe('address4');
  });
});
