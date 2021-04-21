// Tests for the unctions that the project has
// eslint-disable-next-line camelcase
import { cleanup } from '@testing-library/react';
import axios from 'axios';
import getAuthToken from '../../utils/analyticsUtil';

jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

describe('logical testing of token functions', () => {
  afterEach(cleanup);

  it('getAuthToken function', async () => {
    maxios.get.mockResolvedValue({
      data: {
        accessToken: 'token'
      }
    });

    const token = await getAuthToken();

    expect(token).toBe('token');
  });

  it('getAuthToken error', async () => {
    maxios.get.mockRejectedValue('getAuthToken error');

    try {
      await getAuthToken();
    } catch (e) {
      expect(e.message).toEqual('getAuthToken error');
    }
  });
});
