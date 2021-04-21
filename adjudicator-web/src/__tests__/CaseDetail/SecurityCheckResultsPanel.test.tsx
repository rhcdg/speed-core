import React from 'react';
import {
  cleanup,
  render,
  screen,
  fireEvent,
  waitFor
} from '@testing-library/react';
import axios from 'axios';
import SecurityCheckResultsPanel from '../../components/CaseDetail/SecurityCheckResultsPanel';
import { kase } from '../utils/caseData';

const mockUpdateCase = jest.fn();
jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', async () => {
    const response = { data: kase };
    maxios.get.mockResolvedValue(response);

    render(
      <SecurityCheckResultsPanel kase={kase} updateCase={mockUpdateCase} />
    );

    await waitFor(() => {
      const text = screen.getByText('BIOMETRIC_CHECK');
      expect(text).toBeInTheDocument();
    });
  });

  it('calls update case when checkbox is clicked', () => {
    render(
      <SecurityCheckResultsPanel kase={kase} updateCase={mockUpdateCase} />
    );
    const checkbox = screen.getByTestId('securityCheckValidated');
    fireEvent.click(checkbox);
    expect(mockUpdateCase).toHaveBeenCalled();
  });
});
