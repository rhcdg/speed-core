import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import CategoryCard from '../../components/TravelCategories/CategoryCard';

describe('category card', () => {
  it('shows title and text', async () => {
    const contents = { title: 'test title', text: 'test text' };
    render(<CategoryCard contents={contents} />);

    const title = await waitFor(() => screen.getByText(contents.title));
    expect(title).toBeInTheDocument();

    const text = await waitFor(() => screen.getByText(contents.text));
    expect(text).toBeInTheDocument();
  });
});
