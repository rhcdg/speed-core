import axios from 'axios';
import { cleanup } from '@testing-library/react';
import { getCases, getCaseById, postCase } from '../../utils/caseApi';
import { cases, kase } from '../utils/caseData';

jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

describe('getCases api call', () => {
  afterEach(cleanup);

  it('should fetch all cases', () => {
    const response = { data: cases };
    maxios.get.mockResolvedValue(response);
    return getCases()
      .then(resp => {
        expect(resp).toEqual(cases);
      })
      .catch(err => {
        expect(err).not.toBeCalled();
      });
  });
  it('should fetch all cases and error', () => {
    const response = { data: cases };

    maxios.get.mockRejectedValueOnce(new Error('some error'));
    return getCases()
      .then(resp => resp)
      .catch(err => {
        expect(err).toBeCalled();
        expect(err).not.toEqual(response);
      });
  });
});

describe('getCaseById api call', () => {
  afterEach(cleanup);

  it('should fetch a single cases', () => {
    const response = { data: kase };
    maxios.get.mockResolvedValue(response);
    return getCaseById(kase.id)
      .then(resp => {
        expect(resp).toEqual(kase);
      })
      .catch(err => {
        expect(err).not.toBeCalled();
      });
  });

  it('should fetch single case and error', () => {
    const response = { data: kase };

    maxios.get.mockRejectedValueOnce(new Error('some error'));
    return getCaseById('1234')
      .then(resp => resp)
      .catch(err => {
        expect(err).toBeCalled();
        expect(err).not.toEqual(response);
      });
  });
});

describe('postCase api call', () => {
  afterEach(cleanup);

  it('should update a single case', () => {
    const response = { data: kase };
    maxios.post.mockResolvedValue(response);
    return postCase(kase).then(resp => {
      expect(resp).toEqual(kase);
    });
  });
});
