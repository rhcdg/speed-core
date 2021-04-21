import React, { ReactElement } from 'react';
import {
  cleanup,
  fireEvent,
  render,
  screen,
  waitFor
} from '@testing-library/react';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';
import CasesTabPanel from '../../components/CasesPage/CasesTabPanel';
import { cases } from '../utils/caseData';

const mockUpdateAllCases = jest.fn();
jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

const renderWithRouter = (ui: ReactElement) => {
  return render(<MemoryRouter>{ui}</MemoryRouter>);
};

describe('case management tab panel', () => {
  afterEach(cleanup);

  it('shows no cases found when an empty array passed in', () => {
    render(
      <CasesTabPanel
        value={1}
        index={1}
        cases={[]}
        tabRoute="new"
        updateAllCases={mockUpdateAllCases}
        caseId={undefined}
        adjId="123"
      />
    );
    const text = screen.getByText(`No Cases Found.`);

    expect(text).toBeInTheDocument();
  });

  it('displays cases passed in', () => {
    render(
      <CasesTabPanel
        value={1}
        index={1}
        cases={cases}
        tabRoute="new"
        updateAllCases={mockUpdateAllCases}
        caseId={undefined}
        adjId="123"
      />
    );
    const text = screen.getByText(`2021ABC123456791`);

    expect(text).toBeInTheDocument();
  });

  it('shows case detail for a specifc case', async () => {
    const response = { data: cases[0] };
    maxios.get.mockResolvedValue(response);

    const { getByText } = renderWithRouter(
      <CasesTabPanel
        value={1}
        index={1}
        cases={cases}
        caseId="3"
        tabRoute="new"
        updateAllCases={mockUpdateAllCases}
        adjId="123"
      />
    );
    await waitFor(() => {
      const text = getByText(`Case 2021ABC123456789`);
      expect(text).toBeInTheDocument();
    });
  });

  describe('adjudication assignment', () => {
    it('sets adjudicator id when adjudicator is defined', async () => {
      cases[0].adjudicatorId = null;

      const response = { data: cases[0] };
      maxios.put.mockResolvedValue(response);

      const { getByText } = renderWithRouter(
        <CasesTabPanel
          value={1}
          index={1}
          cases={cases}
          caseId="new"
          tabRoute="new"
          updateAllCases={mockUpdateAllCases}
          adjId="123"
        />
      );

      fireEvent.click(getByText('2021ABC123456789'));
      await waitFor(() => {
        expect(mockUpdateAllCases).toHaveBeenCalled();
      });
    });
  });
});
