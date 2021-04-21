import axios from 'axios';
import { Case, CaseHistory } from './caseUtils';
import { environment } from '../environments/environment';
import { getAccessToken } from '../components/AppRouter/AppRouter';

export const getCases = async (): Promise<Case[]> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .get(`${environment.casesURL}/cases/findAll`, config)
    .then(response => response.data)
    .catch(err => {
      return err;
    });
};

export const getCaseById = async (
  caseId: number | string | undefined
): Promise<Case> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .get(`${environment.casesURL}/cases/${caseId}`, config)
    .then(response => {
      return response.data;
    })
    .catch(err => {
      return err;
    });
};

export const postCase = async (kase: Case): Promise<Case> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .post(`${environment.casesURL}/api/data/cases`, kase, config)
    .then(response => response.data);
};

export const getHistory = async (
  caseId: number | string | undefined
): Promise<CaseHistory[]> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .get(`${environment.casesURL}/cases/history`, {
      ...config,
      params: { caseId }
    })
    .then(response => {
      return response.data;
    })
    .catch(err => {
      return err;
    });
};

export const assignCase = async (kase: Case): Promise<Case> => {
  const config = {
    headers: {
      Authorization: `Bearer ${await getAccessToken()}`,
      'Content-Type': 'application/json'
    }
  };

  return axios
    .put(`${environment.casesURL}/cases/assignAdjudicator`, kase, config)
    .then(response => response.data);
};
