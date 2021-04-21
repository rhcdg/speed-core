import React from 'react';
import { Grid } from '@material-ui/core';
import { Case, CaseDetailPanelProps } from '../../utils/caseUtils';
import { getCaseById } from '../../utils/caseApi';

const SecurityCheckResultsPanel: React.FC<CaseDetailPanelProps> = ({
  kase,
  updateCase
}) => {
  const [kaseCopy, setKaseCopy] = React.useState<Case>();

  React.useEffect(() => {
    getCaseById(kase.id)
      .then(resData => {
        setKaseCopy(resData);
      })
      .catch(() => {
        // no-op
      });
  }, [kase.id]);
  return (
    <Grid container className="form-row">
      <table className="table" id="cases-table">
        <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Date Started</th>
            <th scope="col">Date Completed</th>
            <th scope="col">Status</th>
          </tr>
        </thead>
        <tbody>
          {kaseCopy &&
          kaseCopy.securityChecks &&
          kaseCopy.securityChecks.length > 0 ? (
            kaseCopy.securityChecks.map(c => {
              return (
                <tr key={c?.id}>
                  <td>{c?.securityCheckType}</td>
                  <td>{c?.createdAt?.substring(0, 10)}</td>
                  <td>{c?.lastModified?.substring(0, 10) || ''}</td>
                  <td>{c?.securityCheckStatus}</td>
                </tr>
              );
            })
          ) : (
            <></>
          )}
        </tbody>
      </table>
      <Grid container className="form-row">
        <label className="checkbox">
          <input
            type="checkbox"
            name="securityCheckValidated"
            data-testid="securityCheckValidated"
            id="securityCheckValidated"
            checked={kase.securityCheckValidated || false}
            onChange={() => {
              updateCase &&
                updateCase({
                  ...kase,
                  securityCheckValidated: !kase.securityCheckValidated
                });
            }}
          />
          Applicant has passed required security checks?
        </label>
      </Grid>
    </Grid>
  );
};

export default SecurityCheckResultsPanel;
