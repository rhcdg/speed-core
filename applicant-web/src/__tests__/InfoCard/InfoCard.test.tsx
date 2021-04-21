import React from 'react';
import { render } from '@testing-library/react';
import InfoCard from '../../components/InfoCard/InfoCard';

describe('info card', () => {
  test('it renders without crashing', async () => {
    const text = 'test text';
    const contents = <div>{text}</div>;
    const { getByText } = render(
      <InfoCard contents={contents} parent="form" />
    );

    expect(getByText(text)).toBeInTheDocument();
  });
});
