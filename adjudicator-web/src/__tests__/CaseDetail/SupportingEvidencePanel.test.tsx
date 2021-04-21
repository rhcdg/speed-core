import React from 'react';
import { cleanup, render, screen, fireEvent } from '@testing-library/react';
import SupportingEvidencePanel from '../../components/CaseDetail/SupportingEvidencePanel';
import { kase } from '../utils/caseData';

const mockUpdateCase = jest.fn();

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    render(<SupportingEvidencePanel kase={kase} updateCase={mockUpdateCase} />);
    const text = screen.getByText('Application for Employment Authorization');
    expect(text).toBeInTheDocument();
  });

  it('calls update case when checkbox is clicked', () => {
    render(<SupportingEvidencePanel kase={kase} updateCase={mockUpdateCase} />);
    const checkbox = screen.getByTestId('submittedDocs');
    fireEvent.click(checkbox);
    expect(mockUpdateCase).toHaveBeenCalled();
  });
});
