import axios from 'axios';
import { cleanup } from '@testing-library/react';
import { getUserInfo } from '../../utils/userService';

jest.mock('axios');

const maxios = axios as jest.Mocked<typeof axios>;

describe('user service', () => {
  afterEach(cleanup);

  it('should fetch user info', () => {
    const cognitoUsername = 'abc123';
    const users = {
      data: {
        user: {
          active: true,
          email: 'user@test.com',
          firstName: 'Administrator',
          lastName: 'System',
          username: 'admin',
          'cognito:username': cognitoUsername,
          id: 1,
          _links: {
            self: { href: 'http://users:8080/users/api/data/persons/1' },
            person: {
              href: 'http://users:8080/users/api/data/persons/1{?projection}',
              templated: true
            },
            role: { href: 'http://users:8080/users/api/data/persons/1/role' }
          }
        }
      }
    };

    const response = { data: users };

    maxios.get.mockResolvedValue(response);
    return getUserInfo(cognitoUsername)
      .then(resp => {
        expect(resp).toEqual(response.data);
      })
      .catch(err => {
        expect(err).not.toBeCalled();
      });
  });

  it('should fetch users and error', () => {
    const users = {};

    const response = { data: users };

    maxios.get.mockRejectedValueOnce(new Error('some error'));
    return getUserInfo('abc')
      .then(resp => resp)
      .catch(err => {
        expect(err).toBeCalled();
        expect(err).not.toEqual(response);
      });
  });
});
