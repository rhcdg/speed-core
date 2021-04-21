import { cleanup } from '@testing-library/react';
import axios from 'axios';
import {
  I131FormValues,
  IN_PROGRESS,
  formTestData as form
} from '../../utils/formUtils';
import {
  createForm,
  getFormsByUserId,
  patchForm,
  submitForm
} from '../../utils/formService';

jest.mock('axios');
const maxios = axios as jest.Mocked<typeof axios>;

describe('test form utilities', () => {
  afterEach(cleanup);

  describe('submitForm', () => {
    it('resolves success', () => {
      const returnValue = {
        data: form
      };

      maxios.put.mockResolvedValue(returnValue);

      return submitForm(form)
        .then(resp => {
          expect(resp).toEqual(returnValue);
        })
        .catch(err => {
          expect(err).not.toBeCalled();
        });
    });

    it('resolves error', () => {
      const error = 'error';

      maxios.put.mockRejectedValue(error);

      return submitForm(form)
        .then(resp => {
          expect(resp).toEqual(error);
        })
        .catch(err => {
          expect(err).toBeCalled();
        });
    });
  });

  describe('createForm', () => {
    it('resolves success', () => {
      const returnValue = {
        data: form
      };

      maxios.post.mockResolvedValue(returnValue);

      return createForm(form)
        .then(resp => {
          expect(resp).toEqual(form);
        })
        .catch(err => {
          expect(err).not.toBeCalled();
        });
    });

    it('resolves error', () => {
      const error = 'error';

      maxios.post.mockRejectedValue(error);

      return createForm(form)
        .then(resp => {
          expect(resp).toEqual(error);
        })
        .catch(err => {
          expect(err).toBeCalled();
        });
    });
  });

  describe('patchForm', () => {
    it('patchForm', () => {
      const returnValue = {
        data: form
      };

      maxios.patch.mockResolvedValue(returnValue);

      return patchForm(form)
        .then(resp => {
          expect(resp).toEqual(form);
        })
        .catch(err => {
          expect(err).not.toBeCalled();
        });
    });

    it('resolves error', () => {
      const error = 'error';

      maxios.patch.mockRejectedValue(error);

      return patchForm(form)
        .then(resp => {
          expect(resp).toEqual(error);
        })
        .catch(err => {
          expect(err).toBeCalled();
        });
    });
  });

  describe('getFormsByUserId', () => {
    it('getFormsByUserId', () => {
      const forms: I131FormValues[] = [
        {
          ...form,
          formNumber: 'abc123',
          submissionDate: '02-15-2021',
          submissionStatus: IN_PROGRESS
        }
      ];
      const returnValue = { data: { _embedded: { forms } } };

      maxios.get.mockResolvedValue(returnValue);

      return getFormsByUserId('userId')
        .then(resp => {
          expect(resp).toEqual(forms);
        })
        .catch(err => {
          expect(err).not.toBeCalled();
        });
    });

    it('resolves error', () => {
      const error = 'error';

      maxios.get.mockRejectedValue(error);

      return getFormsByUserId('userId')
        .then(resp => {
          expect(resp).toEqual(error);
        })
        .catch(err => {
          expect(err).toBeCalled();
        });
    });
  });
});
