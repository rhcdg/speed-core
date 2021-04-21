import React from 'react';
import { cleanup, render, screen, fireEvent } from '@testing-library/react';
import CaseCorrespondencePanel from '../../components/CaseDetail/CaseCorrespondencePanel';
import { kase } from '../utils/caseData';

const mockUpdateCase = jest.fn();

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    render(<CaseCorrespondencePanel kase={kase} updateCase={mockUpdateCase} />);
    const text = screen.getByText('Application Acknowledgement');
    expect(text).toBeInTheDocument();
  });

  it('calls update case when checkbox is clicked', () => {
    render(<CaseCorrespondencePanel kase={kase} updateCase={mockUpdateCase} />);
    const checkbox = screen.getByTestId('submittedPayment');
    fireEvent.click(checkbox);
    expect(mockUpdateCase).toHaveBeenCalled();
  });
});
