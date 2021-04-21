import React from 'react';
import { Grid, Button, ButtonGroup } from '@material-ui/core';
import { ArrowBack, ArrowForward } from '@material-ui/icons';
import {
  useHistory,
  Switch,
  Route,
  useRouteMatch,
  Link
} from 'react-router-dom';
import './instruction-page.scss';
import Overview from './Sections/Overview';
import Eligibility from './Sections/Eligibility';
import TravelWarning from './Sections/TravelWarning';
import Exceptions from './Sections/Exceptions';
import GeneralInstructions from './Sections/GeneralInstructions';
import Requirements from './Sections/Requirements';
import InitialEvidence from './Sections/InitialEvidence';
import Photographs from './Sections/Photographs';
import BiometricsServices from './Sections/BiometricsServices';
import FilingFees from './Sections/FilingFees';
import AcceptedPayment from './Sections/AcceptedPayment';
import ProcessingInformation from './Sections/ProcessingInformation';
import ExpeditedRequest from './Sections/ExpeditedRequest';
import Penalties from './Sections/Penalties';
import PrivacyNotice from './Sections/PrivacyNotice';
import Decision from './Sections/Decision';
import { FORM_PAGE_URL, INSTRUCTIONS_URL } from '../../utils/routeUrls';
import { routeArray } from './instructions.data';
import Header from '../Header/Header';
import SharedButtons from '../SharedButtons/SharedButtons';

const InstructionPage: React.FC = () => {
  const history = useHistory();
  const { path, url } = useRouteMatch();

  const activeLink = (linkToCheck: string) => {
    const pieces = history.location.pathname.split('/');
    const currentLink = pieces[pieces.length - 1];

    if (currentLink === linkToCheck) {
      return true;
    }
    return false;
  };

  const findActiveLinkIndex = () =>
    routeArray.findIndex(link => activeLink(link));

  const handleForward = () => {
    const index = findActiveLinkIndex();
    history.push(`${INSTRUCTIONS_URL}/${routeArray[index + 1]}`);
  };

  const handleBack = () => {
    const index = findActiveLinkIndex();
    const urlExt =
      index === 1
        ? INSTRUCTIONS_URL
        : `${INSTRUCTIONS_URL}/${routeArray[index - 1]}`;
    history.push(urlExt);
  };

  return (
    <Grid container className="main-content">
      <SharedButtons page="instructions" />
      <Grid item xs={4} />
      <Grid item xs={2}>
        <ButtonGroup variant="text">
          <Button
            onClick={handleBack}
            disabled={activeLink('') || activeLink('instructions')}
          >
            <ArrowBack fontSize="small" />
            &nbsp;Previous
          </Button>
          {activeLink('decision') ? (
            <Link to={FORM_PAGE_URL}>
              <Button variant="contained" color="primary">
                Apply
              </Button>
            </Link>
          ) : (
            <Button onClick={handleForward}>
              Next&nbsp;
              <ArrowForward fontSize="small" />
            </Button>
          )}
        </ButtonGroup>
      </Grid>
      <Header />
      <Grid item xs={3}>
        <Grid container className="left-pane">
          <Grid item>
            <ul>
              <li>
                <Link
                  to={`${url}`}
                  data-testid="overview-link"
                  className={
                    activeLink('') ||
                    activeLink('instructions') ||
                    activeLink('overview') ||
                    activeLink('eligibility') ||
                    activeLink('travel-warning') ||
                    activeLink('exceptions')
                      ? 'active'
                      : ''
                  }
                >
                  Overview
                </Link>
                <ul>
                  <li>
                    <Link
                      to={`${url}/eligibility`}
                      className={activeLink('eligibility') ? 'active' : ''}
                    >
                      Eligibility
                    </Link>
                  </li>
                  <li>
                    <Link
                      to={`${url}/travel-warning`}
                      className={activeLink('travel-warning') ? 'active' : ''}
                    >
                      Travel Warning
                    </Link>
                  </li>
                  <li>
                    <Link
                      to={`${url}/exceptions`}
                      className={activeLink('exceptions') ? 'active' : ''}
                    >
                      Exceptions
                    </Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link
                  to={`${url}/general-instructions`}
                  className={activeLink('general-instructions') ? 'active' : ''}
                >
                  General Instructions
                </Link>
              </li>
              <li>
                <Link
                  to={`${url}/requirements`}
                  className={
                    activeLink('requirements') ||
                    activeLink('initial-evidence') ||
                    activeLink('photographs') ||
                    activeLink('biometrics-services')
                      ? 'active'
                      : ''
                  }
                >
                  Requirements
                </Link>
                <ul>
                  <li>
                    <Link
                      to={`${url}/initial-evidence`}
                      className={activeLink('initial-evidence') ? 'active' : ''}
                    >
                      Initial Evidence
                    </Link>
                  </li>
                  <li>
                    <Link
                      to={`${url}/photographs`}
                      className={activeLink('photographs') ? 'active' : ''}
                    >
                      Photographs
                    </Link>
                  </li>
                  <li>
                    <Link
                      to={`${url}/biometrics-services`}
                      className={
                        activeLink('biometrics-services') ? 'active' : ''
                      }
                    >
                      Biometrics Services
                    </Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link
                  to={`${url}/filing-fees`}
                  className={
                    activeLink('filing-fees') || activeLink('accepted-payment')
                      ? 'active'
                      : ''
                  }
                >
                  Filing Fees
                </Link>
                <ul>
                  <li>
                    <Link
                      to={`${url}/accepted-payment`}
                      className={activeLink('accepted-payment') ? 'active' : ''}
                    >
                      Accepted Payment
                    </Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link
                  to={`${url}/processing-information`}
                  className={
                    activeLink('processing-information') ||
                    activeLink('expedited-request')
                      ? 'active'
                      : ''
                  }
                >
                  Processing Information
                </Link>
                <ul>
                  <li>
                    <Link
                      to={`${url}/expedited-request`}
                      className={
                        activeLink('expedited-request') ? 'active' : ''
                      }
                    >
                      Expedited Request
                    </Link>
                  </li>
                </ul>
              </li>
              <li>
                <Link
                  to={`${url}/penalties`}
                  className={activeLink('penalties') ? 'active' : ''}
                >
                  Penalties
                </Link>
              </li>
              <li>
                <Link
                  to={`${url}/privacy-notice`}
                  className={activeLink('privacy-notice') ? 'active' : ''}
                >
                  Privacy Notice
                </Link>
              </li>
              <li>
                <Link
                  to={`${url}/decision`}
                  className={activeLink('decision') ? 'active' : ''}
                >
                  Decision
                </Link>
              </li>
            </ul>
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={9}>
        <Grid container>
          <Grid item className="instruction-panel">
            <Switch>
              <Route exact path={`${path}`} component={Overview} />
              <Route path={`${path}/eligibility`} component={Eligibility} />
              <Route
                path={`${path}/travel-warning`}
                component={TravelWarning}
              />
              <Route path={`${path}/exceptions`} component={Exceptions} />
              <Route
                path={`${path}/general-instructions`}
                component={GeneralInstructions}
              />
              <Route path={`${path}/requirements`} component={Requirements} />
              <Route
                path={`${path}/initial-evidence`}
                component={InitialEvidence}
              />
              <Route path={`${path}/photographs`} component={Photographs} />
              <Route
                path={`${path}/biometrics-services`}
                component={BiometricsServices}
              />
              <Route path={`${path}/filing-fees`} component={FilingFees} />
              <Route
                path={`${path}/accepted-payment`}
                component={AcceptedPayment}
              />
              <Route
                path={`${path}/processing-information`}
                component={ProcessingInformation}
              />
              <Route
                path={`${path}/expedited-request`}
                component={ExpeditedRequest}
              />
              <Route path={`${path}/penalties`} component={Penalties} />
              <Route
                path={`${path}/privacy-notice`}
                component={PrivacyNotice}
              />
              <Route path={`${path}/decision`} component={Decision} />
            </Switch>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default InstructionPage;
