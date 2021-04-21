import axios, { AxiosResponse } from 'axios';
import { getAccessToken } from '../components/AppRouter/AppRouter';
import { environment } from '../environments/environment';
import { I131FormValues } from './formUtils';

export const getFormsByUserId = async (
  userId: string
): Promise<I131FormValues[]> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      accept: '*/*'
    },
    params: {
      applicantId: userId
    }
  };

  return axios
    .get(`${environment.formsServiceURL}/search/findByApplicantId`, config)
    .then(response => response.data._embedded.forms)
    .catch(err => err);
};

export const patchForm = async (
  formData: I131FormValues
): Promise<I131FormValues> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      accept: '*/*'
    }
  };

  return axios
    .patch(`${environment.formsServiceURL}/${formData.id}`, formData, config)
    .then(response => response.data)
    .catch(err => err);
};

export const createForm = async (
  formData: I131FormValues
): Promise<I131FormValues> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      accept: '*/*'
    }
  };

  return axios
    .post(`${environment.postFormServiceURL}`, formData, config)
    .then(response => response.data)
    .catch(err => err);
};

export const submitForm = async (
  formData: I131FormValues
): Promise<AxiosResponse<I131FormValues>> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .put(`${environment.postFormServiceURL}/submit`, formData, config)
    .then(response => response)
    .catch(err => err);
};
