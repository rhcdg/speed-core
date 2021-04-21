import { Profile, User } from 'oidc-client';
import { environment } from '../environments/environment';

export const isTokenActive = (user: User | null | undefined): boolean => {
  return user && 'expired' in user ? !user.expired : false;
};

export const roleAdmin = 'Administrator';
export const roleDataEntry = 'DataEntry';
export const roleReadOnly = 'ReadOnly';
export const roleSupervisor = 'Supervisor';
export const roleApplicant = 'Applicant';

const extractAuthorityTokens = (profile: Profile): string[] => {
  return environment.cognito.jwtAuthorityKey in profile
    ? (profile as never)[environment.cognito.jwtAuthorityKey]
    : [];
};

export const getUserRoles = (user: User | null | undefined): string[] => {
  return user && isTokenActive(user)
    ? extractAuthorityTokens(user.profile)
    : [];
};

export const hasRole = (
  user: User | null | undefined,
  role?: string
): boolean => (role ? getUserRoles(user).includes(role) : true);
