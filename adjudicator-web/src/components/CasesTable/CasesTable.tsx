import React from 'react';
import { useHistory } from 'react-router-dom';
import { Case } from '../../utils/caseUtils';
import './cases-table.scss';

interface CasesTableProps {
  cases?: Case[] | null;
  assignAdjudicator(kase: Case): void;
}

const CasesTable: React.FC<CasesTableProps> = ({
  cases,
  assignAdjudicator
}) => {
  const history = useHistory();

  const convertDate = (c: Case) => {
    const date = new Date(c.lastModified || c.createdDate);
    return `${`0${date.getMonth() + 1}`.slice(
      -2
    )}-${date.getDate()}-${date.getFullYear()}`;
  };

  return (
    <table className="table" id="cases-table">
      <thead>
        <tr>
          <th scope="col">Case ID</th>
          <th scope="col">Name</th>
          <th scope="col">Type</th>
          <th scope="col">A-Number</th>
          <th scope="col">Date Submitted</th>
          <th scope="col">Last Updated</th>
        </tr>
      </thead>
      <tbody>
        {cases && cases.length > 0 ? (
          cases.map(c => {
            return (
              <tr
                data-testid={`case-row-${c.id}`}
                key={c.id}
                onClick={() => {
                  if (!c.adjudicatorId) {
                    assignAdjudicator(c);
                  }
                  history.push(`/cases/${c.id}`);
                }}
              >
                <td className="cases-table-data">{c.caseNumber}</td>
                <td className="cases-table-data">
                  {c.form ? `${c.form?.lastName}, ${c.form?.firstName}` : ''}
                </td>
                <td className="cases-table-data">
                  {c.formId}
                  {c?.form?.applicationType}
                </td>
                <td className="cases-table-data">{c.anumber}</td>
                <td className="cases-table-data">{convertDate(c)}</td>
                <td className="cases-table-data">{convertDate(c)}</td>
              </tr>
            );
          })
        ) : (
          <tr data-testid="case-table">
            <td colSpan={6} className="cases-table-data-centered">
              No Cases Found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default CasesTable;
