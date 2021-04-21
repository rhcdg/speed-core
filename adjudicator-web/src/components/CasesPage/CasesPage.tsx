import React from 'react';
import { User } from 'oidc-client';
import { Paper, Tabs, Tab, Grid } from '@material-ui/core';
import { useHistory, useParams } from 'react-router-dom';
import { Case, CaseStatus } from '../../utils/caseUtils';
import { getCases } from '../../utils/caseApi';
import CasesTabPanel from './CasesTabPanel';
import SearchBar from '../SearchBar/SearchBar';
import { hasRole, roleAdjudicator } from '../../utils/authenticationUtils';
import NoAccess from '../NoAccess/NoAccess';

import './cases-page.scss';

interface CasesPageProps {
  user?: User | null;
}

interface ParamTypes {
  caseId?: string | undefined;
}

const CasesPage: React.FC<CasesPageProps> = ({ user }) => {
  document.title = 'Case Management Portal';

  const { caseId } = useParams<ParamTypes>();
  const history = useHistory();
  const [value, setValue] = React.useState(0);
  const [allCases, setAllCases] = React.useState<Case[]>([]);
  const [filteredCases, setFilteredCases] = React.useState<Case[]>([]);
  const [searchValue, setSearchValue] = React.useState<string>('');

  React.useEffect(() => {
    if (caseId && caseId === 'new') {
      setValue(0);
    } else if (caseId && caseId === 'my') {
      setValue(1);
    } else if (caseId && caseId === 'completed') {
      setValue(2);
    } else {
      localGetCases();
    }
  }, [caseId]);

  React.useEffect(() => {
    if (searchValue.length === 0) {
      setFilteredCases(allCases);
    } else {
      const searchedCases =
        filteredCases && filteredCases.length > 0
          ? filteredCases.filter(c => {
              return (
                c.anumber.includes(searchValue) ||
                c.caseNumber.includes(searchValue)
              );
            })
          : [];
      setFilteredCases(searchedCases);
    }
  }, [allCases, searchValue]); // eslint-disable-line react-hooks/exhaustive-deps

  const handleChange = (
    _event: React.SyntheticEvent<EventTarget>,
    newValue: number
  ) => {
    if (newValue === 0) {
      history.push(`/cases/new`);
    } else if (newValue === 1) {
      history.push(`/cases/my`);
    } else if (newValue === 2) {
      history.push(`/cases/completed`);
    }
  };

  const updateAllCases = () => {
    localGetCases();
  };

  const localGetCases = () => {
    getCases()
      .then(resData => {
        setAllCases(resData);
      })
      .catch(err => {
        // eslint-disable-next-line no-console
        console.log(err);
      });
  };

  if (user && !hasRole(user, roleAdjudicator)) {
    return <NoAccess />;
  }

  return (
    <div id="main-content" className="main-content">
      <Paper square>
        <Grid container className="case-page-tab">
          <Tabs
            value={value}
            indicatorColor="primary"
            textColor="primary"
            onChange={handleChange}
            aria-label="Cases Management Portal Tabs"
            data-testid="case-management-tabs"
            className="case-tabs"
          >
            <Tab label="New Cases" />
            <Tab label="My Cases" />
            <Tab label="Completed Submissions" />
          </Tabs>
          <SearchBar handleSearch={setSearchValue} />
        </Grid>
        <CasesTabPanel
          value={value}
          index={0}
          cases={
            filteredCases && filteredCases.length > 0
              ? filteredCases.filter(
                  d =>
                    d.adjudicatorId === null &&
                    d.status !== CaseStatus.Validated &&
                    d.status !== CaseStatus.Rejected
                )
              : []
          }
          tabRoute="new"
          caseId={caseId}
          updateAllCases={updateAllCases}
          adjId={user?.profile?.sub}
        />
        <CasesTabPanel
          value={value}
          index={1}
          cases={
            user && filteredCases && filteredCases.length > 0
              ? filteredCases.filter(
                  d =>
                    d.adjudicatorId === user.profile.sub &&
                    d.status !== CaseStatus.Validated &&
                    d.status !== CaseStatus.Rejected
                )
              : []
          }
          tabRoute="new"
          caseId={caseId}
          updateAllCases={updateAllCases}
          adjId={user?.profile?.sub}
        />
        <CasesTabPanel
          value={value}
          index={2}
          cases={
            filteredCases && filteredCases.length > 0
              ? filteredCases.filter(
                  d =>
                    d.status === CaseStatus.Validated ||
                    d.status === CaseStatus.Rejected
                )
              : []
          }
          tabRoute="new"
          caseId={caseId}
          updateAllCases={updateAllCases}
          adjId={user?.profile?.sub}
        />
      </Paper>
    </div>
  );
};

export default CasesPage;
