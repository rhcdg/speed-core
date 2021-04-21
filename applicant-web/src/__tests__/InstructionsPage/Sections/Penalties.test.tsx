import React from 'react';
import { render } from '@testing-library/react';
import Penalties from '../../../components/InstructionPage/Sections/Penalties';

describe('penalties card', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<Penalties />);

    const text =
      'If you knowingly and willfully falsify or conceal a material fact or submit a false document with this request, we will deny your Form I-131 and may deny any other immigration benefit. In addition, you will face severe penalties provided by law and may be subject to criminal prosecution.';

    expect(getByText(text)).toBeInTheDocument();
  });
});
