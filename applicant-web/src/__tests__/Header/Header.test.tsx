import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import Header from '../../components/Header/Header';

describe('shared header', () => {
  test('it renders without crashing', async () => {
    render(<Header />);

    const headerText = 'Advance Parole | Applying Inside the United States';

    const header = await waitFor(() => screen.queryByText(headerText));

    expect(header).toBeInTheDocument();
  });
});
