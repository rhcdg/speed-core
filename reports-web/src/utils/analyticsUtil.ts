import axios from 'axios';
import { getAccessToken } from '../components/AppRouter/AppRouter';
import { environment } from '../environments/environment';

const getAuthToken = async (): Promise<string> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .get(`${environment.analyticsServiceURL}`, config)
    .then(response => response.data.accessToken)
    .catch(err => {
      throw Error(err);
    });
};

export default getAuthToken;
