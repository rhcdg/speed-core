import axios from 'axios';
import { getAccessToken } from '../components/AppRouter/AppRouter';
import { environment } from '../environments/environment';
import { AppPersons } from './usersUtils';

const userData = async (): Promise<AppPersons[]> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .get(`${environment.userServiceURL}/persons`, config)
    .then(response => response.data._embedded.persons)
    .catch(err => {
      return err;
    });
};
export default userData;
