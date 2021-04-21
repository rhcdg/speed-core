import React from 'react';
import {
  cleanup,
  render,
  fireEvent,
  waitFor,
  screen
} from '@testing-library/react';
import ReactRouter from 'react-router';
import { createMemoryHistory } from 'history';
import CasesTable from '../../components/CasesTable/CasesTable';
import { cases } from '../utils/caseData';

const mockAssignAdjudicator = jest.fn();

describe('case table', () => {
  afterEach(cleanup);

  it('shows new cases', async () => {
    const { getByText } = render(
      <CasesTable cases={cases} assignAdjudicator={mockAssignAdjudicator} />
    );
    await waitFor(() => {
      expect(getByText('2021ABC123456791')).toBeInTheDocument();
    });
  });

  it('routes to detail page when entry is clicked', async () => {
    const history = createMemoryHistory();
    jest.spyOn(ReactRouter, 'useHistory').mockReturnValue(history);
    render(
      <CasesTable cases={cases} assignAdjudicator={mockAssignAdjudicator} />
    );
    const detailRow = screen.getByTestId(`case-row-1`);
    fireEvent.click(detailRow, new MouseEvent('click', { bubbles: true }));
    expect(history.location.pathname).toEqual('/cases/1');
  });

  it('assigns adjudicator when entry is clicked', async () => {
    cases[0].adjudicatorId = null;
    const history = createMemoryHistory();
    jest.spyOn(ReactRouter, 'useHistory').mockReturnValue(history);
    render(
      <CasesTable cases={cases} assignAdjudicator={mockAssignAdjudicator} />
    );
    const detailRow = screen.getByTestId(`case-row-1`);
    fireEvent.click(detailRow, new MouseEvent('click', { bubbles: true }));
    expect(mockAssignAdjudicator).toHaveBeenCalled();
  });
});
