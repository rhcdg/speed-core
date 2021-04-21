import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import SignaturePanel from '../../components/CaseDetail/SignaturePanel';
import { kase } from '../utils/caseData';

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    render(<SignaturePanel kase={kase} />);
    const input = screen.getByLabelText('Daytime Phone') as HTMLInputElement;
    expect(input.value).toBe('444-444-4444');
  });
});
