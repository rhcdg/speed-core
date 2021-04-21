import React from 'react';
import axios from 'axios';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import CaseHistoryPanel from '../../components/CaseDetail/CaseHistoryPanel';
import { kase, caseHistory } from '../utils/caseData';

jest.mock('axios');

const maxios = axios as jest.Mocked<typeof axios>;

describe('shows application information detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', async () => {
    const response = { data: caseHistory };
    maxios.get.mockResolvedValue(response);
    render(<CaseHistoryPanel kase={kase} />);
    await waitFor(() => {
      const text = screen.getByText('FEE_RECEIPT');
      expect(text).toBeInTheDocument();
    });
  });
});
