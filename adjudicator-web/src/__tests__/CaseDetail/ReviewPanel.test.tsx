import React from 'react';
import { cleanup, render, fireEvent, waitFor } from '@testing-library/react';
import ReviewPanel from '../../components/CaseDetail/ReviewPanel';
import { kase } from '../utils/caseData';

const mockUpdateCase = jest.fn();

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    kase.decisionJustification = 'This is my justification';
    const { getByText } = render(
      <ReviewPanel kase={kase} updateCase={mockUpdateCase} />
    );
    const text = getByText('This is my justification');
    expect(text).toBeInTheDocument();
  });

  it('does not allow denying if no justification provided', async () => {
    kase.decisionJustification = null;
    const { getByTestId, getByText } = render(
      <ReviewPanel kase={kase} updateCase={mockUpdateCase} />
    );
    fireEvent.click(getByTestId('deny-case'));
    expect(
      getByText('You need to provide a justification before denying.')
    ).toBeInTheDocument();
    fireEvent.click(getByTestId('deny-dialog-confirm'));
    await waitFor(() => {
      expect(() =>
        getByText('You need to provide a justification before denying.')
      ).toThrow();
    });
  });

  it('allows denying if no justification provided', async () => {
    kase.decisionJustification = null;
    const { getByTestId, getByText } = render(
      <ReviewPanel kase={kase} updateCase={mockUpdateCase} />
    );
    fireEvent.change(getByTestId('justification-textarea'), {
      target: { value: 'testing testing testing' }
    });
    expect(getByText('testing testing testing')).toBeInTheDocument();
    fireEvent.click(getByTestId('deny-case'));
    expect(mockUpdateCase).toHaveBeenCalled();
  });

  it('does not allow approving if no justification provided', async () => {
    kase.decisionJustification = null;
    const { getByTestId, getByText } = render(
      <ReviewPanel kase={kase} updateCase={mockUpdateCase} />
    );
    fireEvent.click(getByTestId('approve-case'));
    expect(getByText('Provide a justification')).toBeInTheDocument();
    fireEvent.click(getByTestId('approve-dialog-confirm'));
    await waitFor(() => {
      expect(() => getByText('Provide a justification')).toThrow();
    });
  });

  it('allows approving if no justification provided', async () => {
    kase.decisionJustification = null;
    kase.caseCorrespondenceValidated = true;
    kase.supportingEvidenceValidated = true;
    kase.securityCheckValidated = true;
    const { getByTestId, getByText } = render(
      <ReviewPanel kase={kase} updateCase={mockUpdateCase} />
    );
    fireEvent.change(getByTestId('justification-textarea'), {
      target: { value: 'testing testing testing' }
    });
    expect(getByText('testing testing testing')).toBeInTheDocument();
    fireEvent.click(getByTestId('approve-case'));
    expect(mockUpdateCase).toHaveBeenCalled();
  });
});
