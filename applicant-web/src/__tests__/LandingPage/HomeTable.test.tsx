import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import HomeTable, {
  FormTableRow
} from '../../components/LandingPage/HomeTable';

describe('landing page', () => {
  afterEach(cleanup);

  it('shows submitted title', async () => {
    const tableType = 'submitted';

    await act(async () => {
      render(<HomeTable tableType={tableType} rows={[]} />);
    });
    const title = screen.getByText('Submitted Applications');

    expect(title).toBeInTheDocument();
  });

  it('shows in progress title', async () => {
    const tableType = 'in-progress';

    await act(async () => {
      render(<HomeTable tableType={tableType} rows={[]} />);
    });
    const title = screen.getByText('Applications In Progress');

    expect(title).toBeInTheDocument();
  });

  it('shows forms table data', async () => {
    const row: FormTableRow = {
      formNumber: 'abc123',
      submissionDate: '03-23-2018',
      submissionStatus: 'Expired'
    };
    const rows = [row];

    await act(async () => {
      render(<HomeTable tableType="submitted" rows={rows} />);
    });
    const dataText = screen.getByText(row.formNumber);

    expect(dataText).toBeInTheDocument();
  });

  it('shows "no forms" message if empty rows prop', async () => {
    const rows: FormTableRow[] = [];

    await act(async () => {
      render(<HomeTable tableType="submitted" rows={rows} />);
    });
    const dataText = screen.getByText('No forms found');

    expect(dataText).toBeInTheDocument();
  });
});
