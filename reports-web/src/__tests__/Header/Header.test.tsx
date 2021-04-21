import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import Header from '../../components/Header/Header';

describe('subHeader', () => {
  const testText = 'Test Text';

  test('it renders without crashing', async () => {
    const subHeader = render(<Header text={testText} />);

    expect(subHeader).not.toBeUndefined();
  });

  test('it shows header text', async () => {
    render(<Header text={testText} />);

    const text = await waitFor(() => screen.queryByText(testText));

    expect(text).toBeInTheDocument();
  });
});
