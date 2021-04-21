import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import TravelCategories from '../../components/TravelCategories/TravelCategories';

describe('travel categories page', () => {
  it('shows header text', async () => {
    const componentHeaderText =
      'This form is for applying to U.S. Citizenship and Immigration Services (USCIS) for the following travel documents:';
    render(<TravelCategories />);
    const text = await waitFor(() => screen.queryByText(componentHeaderText));
    expect(text).toBeInTheDocument();
  });

  it('has 4 cards', async () => {
    render(<TravelCategories />);

    const cards = await waitFor(() => screen.findAllByText('Instructions'));
    expect(cards).toHaveLength(4);
  });
});
