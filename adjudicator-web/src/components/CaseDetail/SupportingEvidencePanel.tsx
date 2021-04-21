import React from 'react';
import { Grid } from '@material-ui/core';
import { CaseDetailPanelProps } from '../../utils/caseUtils';

const SupportingEvidencePanel: React.FC<CaseDetailPanelProps> = ({
  kase,
  updateCase
}) => {
  return (
    <Grid container className="form-row">
      <table className="table" id="cases-table">
        <thead>
          <tr>
            <th scope="col">Form</th>
            <th scope="col">Document Name</th>
            <th scope="col">Date Submitted</th>
            <th scope="col">Status</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td />
            <td>Photo Identification</td>
            <td>03-24-2021</td>
            <td />
          </tr>
          <tr>
            <td>I-765</td>
            <td>Application for Employment Authorization</td>
            <td>03-24-2021</td>
            <td>Pending</td>
          </tr>
          <tr>
            <td>I-485</td>
            <td>Application to Register Permanent Residence</td>
            <td>03-24-2021</td>
            <td>Pending</td>
          </tr>
          <tr>
            <td>I-130</td>
            <td>Petition for Alien Relative</td>
            <td>03-24-2021</td>
            <td>Pending</td>
          </tr>
          <tr>
            <td>G-1450</td>
            <td>Authorization for Credit Card Transactions</td>
            <td>03-24-2021</td>
            <td>Pending</td>
          </tr>
        </tbody>
      </table>
      <Grid container className="form-row">
        <label className="checkbox">
          <input
            type="checkbox"
            name="submittedDocs"
            data-testid="submittedDocs"
            id="submittedDocs"
            checked={kase.supportingEvidenceValidated}
            onChange={() =>
              updateCase &&
              updateCase({
                ...kase,
                supportingEvidenceValidated: !kase.supportingEvidenceValidated
              })
            }
          />
          Applicant has submitted required documentation?
        </label>
      </Grid>
    </Grid>
  );
};

export default SupportingEvidencePanel;
