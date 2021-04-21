// Tests for the unctions that the project has
// eslint-disable-next-line camelcase
import { cleanup } from '@testing-library/react';
import {
  getUserRoles,
  hasRole,
  isTokenActive,
  isTokenAdmin,
  roleAdmin,
  roleReadOnly
} from '../../utils/authenticationUtils';

describe('logical testing of token functions', () => {
  const userAdmin = {
    expired: false,
    profile: {
      'cognito:groups': [roleAdmin]
    }
  };

  const userReadOnly = {
    expired: false,
    profile: {
      'cognito:groups': [roleReadOnly]
    }
  };

  const userExpired = {
    expired: true,
    profile: {
      'cognito:groups': [roleAdmin]
    }
  };

  const userInvalid = {
    profile: {}
  };

  afterEach(cleanup);

  it('getUserRoles function', () => {
    expect(getUserRoles(userAdmin as never)).toStrictEqual([roleAdmin]);
    expect(getUserRoles(userInvalid as never)).toStrictEqual([]);
    expect(getUserRoles(userExpired as never)).toStrictEqual([]);
    expect(getUserRoles(null)).toStrictEqual([]);
  });

  it('hasRole function', () => {
    expect(hasRole(userAdmin as never, roleAdmin)).toBe(true);
    expect(hasRole(userAdmin as never, roleReadOnly)).toBe(false);
    expect(hasRole(userInvalid as never, roleReadOnly)).toBe(false);
    expect(hasRole(userExpired as never, roleAdmin)).toBe(false);
    expect(hasRole(null, roleReadOnly)).toBe(false);
  });

  it('isTokenActive function', () => {
    expect(isTokenActive(userAdmin as never)).toBe(true);
    expect(isTokenActive(userInvalid as never)).toBe(false);
    expect(isTokenActive(userExpired as never)).toBe(false);
    expect(isTokenActive(null)).toBe(false);
  });

  it('isTokenAdmin function', () => {
    expect(isTokenAdmin(userAdmin as never)).toBe(true);
    expect(isTokenAdmin(userReadOnly as never)).toBe(false);
    expect(isTokenAdmin(userInvalid as never)).toBe(false);
    expect(isTokenAdmin(userExpired as never)).toBe(false);
    expect(isTokenAdmin(null)).toBe(false);
  });
});
