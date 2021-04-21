import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import Footer from '../../components/Footer/Footer';

describe('login page', () => {
  afterEach(cleanup);

  it('renders Team Steampunk name', () => {
    render(<Footer />);
    const strongElement = screen.getByText(
      /U.S. Citizenship and Immigration Services/i
    );
    expect(strongElement).toBeInTheDocument();
  });
});
