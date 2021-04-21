import React from 'react';
import { Grid } from '@material-ui/core';
import { CaseDetailPanelProps } from '../../utils/caseUtils';

const CaseCorrespondencePanel: React.FC<CaseDetailPanelProps> = ({
  kase,
  updateCase
}) => {
  return (
    <Grid container className="form-row">
      <table className="table" id="cases-table">
        <thead>
          <tr>
            <th scope="col">Kind</th>
            <th scope="col">Document Name</th>
            <th scope="col">Date Received/Generated</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>PDF</td>
            <td>Application</td>
            <td>03-23-2021</td>
          </tr>
          <tr>
            <td>PDF</td>
            <td>Application Acknowledgement</td>
            <td>03-23-2021</td>
          </tr>
          <tr>
            <td>PDF</td>
            <td>Receipt of Payment</td>
            <td>03-23-2021</td>
          </tr>
        </tbody>
      </table>
      <Grid container className="form-row">
        <label className="checkbox">
          <input
            type="checkbox"
            name="submittedPayment"
            data-testid="submittedPayment"
            id="submittedPayment"
            checked={kase.caseCorrespondenceValidated}
            onChange={() => {
              updateCase &&
                updateCase({
                  ...kase,
                  caseCorrespondenceValidated: !kase.caseCorrespondenceValidated
                });
            }}
          />
          Applicant has submitted required payment?
        </label>
      </Grid>
    </Grid>
  );
};

export default CaseCorrespondencePanel;
