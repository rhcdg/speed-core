import axios from 'axios';
import { getAccessToken } from '../components/AppRouter/AppRouter';
import { environment } from '../environments/environment';
import { Person } from './usersUtils';

export const getUserInfo = async (userId: string): Promise<Person> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      accept: '*/*'
    },
    params: {
      validatorUserId: userId
    }
  };

  return axios
    .get(`${environment.accountServiceURL}`, config)
    .then(response => response.data)
    .catch(err => {
      return err;
    });
};
