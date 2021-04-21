import React from 'react';
import {
  Grid,
  Typography,
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  DialogContentText,
  DialogActions,
  FadeProps
} from '@material-ui/core';

import {
  useHistory,
  Switch,
  Route,
  useRouteMatch,
  Link
} from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

import { Case } from '../../utils/caseUtils';
import './case-detail.scss';
import ApplicationInformationPanel from './ApplicationInformationPanel';
import ApplicationTypePanel from './ApplicationTypePanel';
import TravelInformationPanel from './TravelInformationPanel';
import SignaturePanel from './SignaturePanel';
import SupportingEvidencePanel from './SupportingEvidencePanel';
import CaseCorrespondencePanel from './CaseCorrespondencePanel';
import CaseHistoryPanel from './CaseHistoryPanel';
import SecurityCheckResultsPanel from './SecurityCheckResultsPanel';
import ReviewPanel from './ReviewPanel';
import { postCase } from '../../utils/caseApi';

library.add(faArrowLeft);

interface CaseDetailProps {
  kase: Case;
  prevRoute: string | undefined;
  updateAllCases: () => void;
}

const CaseDetail: React.FC<CaseDetailProps> = ({
  kase,
  prevRoute,
  updateAllCases
}) => {
  const history = useHistory();
  const { path, url } = useRouteMatch();
  const [workingCase, setWorkingCase] = React.useState<Case>(kase);
  const [successOpen, setSuccessOpen] = React.useState<boolean>(false);

  const activeLink = (linkToCheck: string) => {
    const pieces = history.location.pathname.split('/');
    const currentLink = pieces[pieces.length - 1];
    const regex = new RegExp(/^\d+$/);

    if (currentLink === linkToCheck) {
      return true;
    }
    if (linkToCheck === '' && regex.test(currentLink)) {
      return true;
    }
    return false;
  };

  const updateCase = (c: Case, showSuccess = false) => {
    setWorkingCase(c);
    postCase(c)
      .then(resData => {
        setWorkingCase(resData);
        updateAllCases();

        if (showSuccess) {
          setSuccessOpen(true);
        }
      })
      .catch(err => {
        // eslint-disable-next-line no-console
        console.log(err);
      });
  };

  return (
    <Grid container className="case-details-container">
      <Grid item xs={3}>
        <Grid container className="left-pane">
          <Grid
            item
            className="go-back"
            data-testid="go-back"
            onClick={() => history.push(`/cases/${prevRoute}`)}
          >
            <FontAwesomeIcon icon="arrow-left" />
            &nbsp; Results
          </Grid>
          <Grid item>
            <ul>
              <li>
                <Link
                  to={`${url}`}
                  data-testid="application-information-link"
                  className={
                    activeLink('') ||
                    activeLink('application_type') ||
                    activeLink('travel_information') ||
                    activeLink('signature')
                      ? 'active'
                      : ''
                  }
                >
                  Application Information
                </Link>
                <ul>
                  <li>
                    <Link
                      to={`${url}/application_type`}
                      className={activeLink('application_type') ? 'active' : ''}
                    >
                      Application Type
                    </Link>
                  </li>
                  <li>
                    <Link
                      to={`${url}/travel_information`}
                      className={
                        activeLink('travel_information') ? 'active' : ''
                      }
                    >
                      Travel Information
                    </Link>
                  </li>
                  <li>
                    <Link
                      to={`${url}/signature`}
                      className={activeLink('signature') ? 'active' : ''}
                    >
                      Signature
                    </Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link
                  to={`${url}/supporting_evidence`}
                  className={activeLink('supporting_evidence') ? 'active' : ''}
                >
                  Supporting Evidence
                </Link>
              </li>
              <li>
                <Link
                  to={`${url}/case_correspondence`}
                  className={activeLink('case_correspondence') ? 'active' : ''}
                >
                  Case Correspondence
                </Link>
              </li>
              <li>
                <Link
                  to={`${url}/case_history`}
                  className={
                    activeLink('case_history') ||
                    activeLink('security_check_results')
                      ? 'active'
                      : ''
                  }
                >
                  Case History
                </Link>
                <ul>
                  <li>
                    <Link
                      to={`${url}/security_check_results`}
                      className={
                        activeLink('security_check_results') ? 'active' : ''
                      }
                    >
                      Security Check Results
                    </Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link
                  to={`${url}/review`}
                  className={activeLink('review') ? 'active' : ''}
                >
                  Review &amp; Submit
                </Link>
              </li>
            </ul>
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={9}>
        <Grid container>
          <Grid item className="case-number">
            <Typography variant="h5">Case {kase.caseNumber}</Typography>
          </Grid>
          <Grid item className="case-details-panel">
            <Switch>
              <Route
                exact
                path={path}
                render={props => (
                  <ApplicationInformationPanel {...props} kase={workingCase} />
                )}
              />
              <Route
                path={`${path}/application_type`}
                render={props => (
                  <ApplicationTypePanel {...props} kase={workingCase} />
                )}
              />
              <Route
                path={`${path}/travel_information`}
                render={props => (
                  <TravelInformationPanel {...props} kase={workingCase} />
                )}
              />
              <Route
                path={`${path}/signature`}
                render={props => (
                  <SignaturePanel {...props} kase={workingCase} />
                )}
              />
              <Route
                path={`${path}/supporting_evidence`}
                render={props => (
                  <SupportingEvidencePanel
                    {...props}
                    kase={workingCase}
                    updateCase={updateCase}
                  />
                )}
              />
              <Route
                path={`${path}/case_correspondence`}
                render={props => (
                  <CaseCorrespondencePanel
                    {...props}
                    kase={workingCase}
                    updateCase={updateCase}
                  />
                )}
              />
              <Route
                path={`${path}/case_history`}
                render={props => (
                  <CaseHistoryPanel {...props} kase={workingCase} />
                )}
              />
              <Route
                path={`${path}/security_check_results`}
                render={props => (
                  <SecurityCheckResultsPanel
                    {...props}
                    kase={workingCase}
                    updateCase={updateCase}
                  />
                )}
              />
              <Route
                path={`${path}/review`}
                render={props => (
                  <ReviewPanel
                    {...props}
                    kase={workingCase}
                    updateCase={updateCase}
                  />
                )}
              />
            </Switch>
          </Grid>
        </Grid>
      </Grid>
      <Dialog
        open={successOpen}
        TransitionProps={{ role: 'presentation' } as FadeProps}
        aria-labelledby="success-dialog-title"
        aria-describedby="success-dialog-description"
      >
        <DialogTitle id="success-dialog-title">
          Case Review Completed
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="success-dialog-description">
            Your review of the case has successfully been recorded.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              setSuccessOpen(false);
              history.push('/cases/my');
            }}
            data-testid="logout-dialog-confirm"
            color="primary"
            autoFocus
          >
            OK
          </Button>
        </DialogActions>
      </Dialog>
    </Grid>
  );
};

export default CaseDetail;
