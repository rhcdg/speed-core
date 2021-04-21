import React from 'react';
import { CaseDetailPanelProps, CaseHistory } from '../../utils/caseUtils';
import { getHistory } from '../../utils/caseApi';

const CaseHistoryPanel: React.FC<CaseDetailPanelProps> = ({ kase }) => {
  const [caseHistory, setCaseHistory] = React.useState<CaseHistory[]>([]);

  React.useEffect(() => {
    getHistory(kase.id)
      .then(resData => {
        setCaseHistory(resData);
      })
      .catch(err => {
        // eslint-disable-next-line no-console
        console.log(err);
      });
  }, [kase.id]);

  return (
    <table className="table" id="cases-table">
      <thead>
        <tr>
          <th scope="col">Event</th>
          <th scope="col">Date Started</th>
          <th scope="col">Time</th>
          <th scope="col">Ajudicator</th>
        </tr>
      </thead>
      <tbody>
        {caseHistory && caseHistory.length > 0 ? (
          caseHistory.map(ch => {
            return (
              <tr key={ch?.revNumber}>
                <td>{ch?.event}</td>
                <td>{ch?.modDate?.substring(0, 10) || ''}</td>
                <td>{ch?.modDate?.substring(11, 16)}</td>
                <td>{ch?.adjudicatorName}</td>
              </tr>
            );
          })
        ) : (
          <></>
        )}
      </tbody>
    </table>
  );
};

export default CaseHistoryPanel;
