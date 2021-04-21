import React from 'react';
import { render, waitFor } from '@testing-library/react';
import SharedButtons from '../../components/SharedButtons/SharedButtons';

describe('shared buttons', () => {
  test('it renders instructions active', async () => {
    const { getByTestId } = render(<SharedButtons page="instructions" />);

    const divider = await waitFor(() => getByTestId('instructions-active'));

    expect(divider).toBeInTheDocument();
  });

  test('it renders form active', async () => {
    const { getByTestId } = render(<SharedButtons page="form" />);

    const divider = await waitFor(() => getByTestId('form-active'));

    expect(divider).toBeInTheDocument();
  });
});
