import React from 'react';
import { getCaseById, assignCase } from '../../utils/caseApi';
import CaseDetail from '../CaseDetail/CaseDetail';
import CasesTable from '../CasesTable/CasesTable';
import { Case } from '../../utils/caseUtils';

interface CasesTabPanelProps {
  value: number;
  index: number;
  cases?: Case[] | null;
  tabRoute: string;
  caseId: string | undefined;
  updateAllCases: () => void;
  adjId: string | undefined;
}

const CasesTabPanel: React.FC<CasesTabPanelProps> = ({
  value,
  index,
  cases,
  tabRoute,
  caseId,
  updateAllCases,
  adjId
}) => {
  const [kase, setKase] = React.useState<Case | null>();

  React.useEffect(() => {
    if (![undefined, 'new', 'my', 'completed'].includes(caseId)) {
      getCaseById(caseId)
        .then(resData => {
          setKase(resData);
        })
        .catch(() => {
          // no-op
        });
    } else {
      setKase(null);
    }
  }, [caseId]);

  const assignAdjudicator = (c: Case) => {
    if (adjId && c) {
      const caseCopy = c;
      caseCopy.adjudicatorId = adjId;
      assignCase(caseCopy)
        .then(() => {
          updateAllCases();
        })
        .catch(() => {
          // no-op
        });
    }
  };

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tabpanel-${index}`}
    >
      {kase ? (
        <CaseDetail
          kase={kase}
          prevRoute={tabRoute}
          updateAllCases={updateAllCases}
        />
      ) : (
        value === index && (
          <CasesTable cases={cases} assignAdjudicator={assignAdjudicator} />
        )
      )}
    </div>
  );
};

export default CasesTabPanel;
