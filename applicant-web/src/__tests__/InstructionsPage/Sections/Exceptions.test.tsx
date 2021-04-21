import React from 'react';
import { render } from '@testing-library/react';
import Exceptions from '../../../components/InstructionPage/Sections/Exceptions';

describe('exceptions card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<Exceptions />);

    const text =
      'If you are in the United States and seek an Advance Parole Document, a document may not be issued to you if:';

    expect(getByText(text)).toBeInTheDocument();
  });
});
