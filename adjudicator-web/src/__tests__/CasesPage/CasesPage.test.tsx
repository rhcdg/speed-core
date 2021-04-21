import React from 'react';
import { cleanup, render, fireEvent, waitFor } from '@testing-library/react';
import axios from 'axios';
import ReactRouter from 'react-router';
import { createMemoryHistory } from 'history';
import CasesPage from '../../components/CasesPage/CasesPage';
import * as Utils from '../../utils/authenticationUtils';
import { cases } from '../utils/caseData';

jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

describe('case management page', () => {
  afterEach(cleanup);

  beforeEach(() => {
    const history = createMemoryHistory();
    jest
      .spyOn(Utils, 'getUserRoles')
      .mockImplementation(() => [Utils.roleAdjudicator]);

    jest.spyOn(ReactRouter, 'useParams').mockReturnValue({ caseId: undefined });
    jest.spyOn(ReactRouter, 'useHistory').mockReturnValue(history);

    const response = { data: cases };
    maxios.get.mockResolvedValue(response);
  });

  describe('tabs', () => {
    it('default to New Cases as the active tab', async () => {
      const { getByText } = render(<CasesPage />);
      await waitFor(() => {
        const newCasesTab = getByText('New Cases').closest('button');
        expect(
          newCasesTab && newCasesTab.classList.contains('Mui-selected')
        ).toBe(true);
      });
    });

    it('highlights My Cases as active tab when clicked', async () => {
      const { getByText } = render(<CasesPage />);
      await waitFor(() => {
        jest.spyOn(ReactRouter, 'useParams').mockReturnValue({ caseId: 'my' });
        const myCases = getByText('My Cases');
        expect(myCases).toBeInTheDocument();
        fireEvent.click(myCases, new MouseEvent('click', { bubbles: true }));
        const myCasesTab = myCases.closest('button');
        expect(
          myCasesTab && myCasesTab.classList.contains('Mui-selected')
        ).toBe(true);
      });
    });

    it('highlights Completed Submissions as active tab when clicked', async () => {
      const { getByText } = render(<CasesPage />);

      await waitFor(() => {
        jest
          .spyOn(ReactRouter, 'useParams')
          .mockReturnValue({ caseId: 'completed' });
        const completed = getByText('Completed Submissions');
        expect(completed).toBeInTheDocument();
        fireEvent.click(completed, new MouseEvent('click', { bubbles: true }));
        const completedTab = completed.closest('button');
        expect(
          completedTab && completedTab.classList.contains('Mui-selected')
        ).toBe(true);
      });
    });

    it('highlights New Cases when on a different tab and New Cases is selected', async () => {
      const { getByText } = render(<CasesPage />);
      await waitFor(() => {
        jest.spyOn(ReactRouter, 'useParams').mockReturnValue({ caseId: 'my' });
        const myCases = getByText('My Cases');
        expect(myCases).toBeInTheDocument();
        fireEvent.click(myCases, new MouseEvent('click', { bubbles: true }));
        jest.spyOn(ReactRouter, 'useParams').mockReturnValue({ caseId: 'new' });
        const newCases = getByText('New Cases');
        fireEvent.click(newCases, new MouseEvent('click', { bubbles: true }));
        const newCasesTab = newCases.closest('button');
        expect(
          newCasesTab && newCasesTab.classList.contains('Mui-selected')
        ).toBe(true);
      });
    });
  });

  describe('case data', () => {
    it('shows new cases', async () => {
      const { getByText } = render(<CasesPage />);
      await waitFor(() => {
        expect(getByText('2021ABC123456791')).toBeInTheDocument();
      });
    });

    it('allows for search by anumber', async () => {
      const { getByText, getByTestId } = render(<CasesPage />);
      await waitFor(() => {
        expect(getByText('A987654321')).toBeInTheDocument();
        expect(getByText('A000000000')).toBeInTheDocument();
      });
      await waitFor(() => {
        const input = getByTestId('search-bar');
        fireEvent.change(input, { target: { value: '00000' } });
        expect(() => getByText('A987654321')).toThrow();
        expect(getByText('A000000000')).toBeInTheDocument();
      });
    });

    it('allows for search by case number', async () => {
      const { getByText, getByTestId } = render(<CasesPage />);
      await waitFor(() => {
        expect(getByText('2021ABC123456790')).toBeInTheDocument();
        expect(getByText('2021ABC123456791')).toBeInTheDocument();
      });
      await waitFor(() => {
        const input = getByTestId('search-bar');
        fireEvent.change(input, { target: { value: '2021ABC123456790' } });
        expect(() => getByText('2021ABC123456791')).toThrow();
        expect(getByText('2021ABC123456790')).toBeInTheDocument();
      });
    });

    it('shows no cases found when search returns 0 records', async () => {
      const { getByText, getByTestId } = render(<CasesPage />);
      await waitFor(() => {
        expect(getByText('2021ABC123456790')).toBeInTheDocument();
        expect(getByText('2021ABC123456791')).toBeInTheDocument();
      });
      await waitFor(() => {
        const input = getByTestId('search-bar');
        fireEvent.change(input, { target: { value: 'VALUE-NOT-IN-THE-DATA' } });
        expect(() => getByText('2021ABC123456790')).toThrow();
        expect(getByText('No Cases Found.')).toBeInTheDocument();
      });
    });
  });
});
