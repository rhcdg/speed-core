import React, { useEffect } from 'react';

import { useAnalyticsApi, useAuthorize } from 'react-use-analytics-api';

import {
  PieChart,
  SessionsByDateChart,
  SessionsGeoChart,
  TableChart
} from 'react-analytics-charts';

import { Grid, makeStyles, Paper, Typography } from '@material-ui/core';
import getAuthToken from '../../../utils/analyticsUtil';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary
  }
}));

const BusinessIntelligence: React.FC = () => {
  const { ready, gapi, authorized, error } = useAnalyticsApi();

  const [authToken, setAuthToken] = React.useState<string>('');
  useEffect(() => {
    if (authToken === '') {
      getAuthToken()
        .then(response => setAuthToken(response))
        .catch(() => {
          setAuthToken('');
        });
    }
  });

  const authorize = useAuthorize(gapi, {
    serverAuth: {
      access_token: authToken
    }
  });

  useEffect(() => {
    if (ready && !error && authToken !== '') {
      authorize();
    }
  }, [authorize, authToken, error, ready]);

  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Active Sessions</Typography>
            {authorized && (
              <SessionsByDateChart gapi={gapi} viewId="ga:240276519" />
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">User Locations</Typography>
            {authorized && (
              <SessionsGeoChart gapi={gapi} viewId="ga:240276519" />
            )}
          </Paper>
        </Grid>

        <Grid item xs={12}>
          <Typography variant="h6">Device Analytics and Metrics</Typography>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Browsers Per Session</Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:browser'
                  }}
                  container="browsers-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:browser'
                  }}
                  container="browsers-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Operating System Per Session</Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:operatingSystem'
                  }}
                  container="operating-system-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:operatingSystem'
                  }}
                  container="operating-system-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Mobile Device Per Session</Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:mobileDeviceModel'
                  }}
                  container="mobile-device-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:mobileDeviceModel'
                  }}
                  container="mobile-device-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">
              Mobile Device Information Per Session
            </Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:mobileDeviceInfo'
                  }}
                  container="mobile-device-info-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:mobileDeviceInfo'
                  }}
                  container="mobile-device-info-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Device Category Per Session</Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:deviceCategory'
                  }}
                  container="device-category-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:deviceCategory'
                  }}
                  container="device-category-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12}>
          <Typography variant="h6">App Analytics and Metrics</Typography>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">
              Unique Screen Views Per Session
            </Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:uniqueScreenviews'
                  }}
                  container="uniqueScreenviews-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:uniqueScreenviews'
                  }}
                  container="uniqueScreenviews-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Screen Views Per Session</Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:screenviewsPerSession'
                  }}
                  container="screenviews-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:screenviewsPerSession'
                  }}
                  container="screenviews-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Time on Screen Per Session</Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:timeOnScreen'
                  }}
                  container="timeOnScreen-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:timeOnScreen'
                  }}
                  container="timeOnScreen-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">
              Average Screen View Per Session
            </Typography>
            {authorized && (
              <>
                <PieChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:avgScreenviewDuration'
                  }}
                  container="avgScreenviewDuration-per-session-chart"
                />

                <TableChart
                  gapi={gapi}
                  query={{
                    ids: 'ga:240276519',
                    'start-date': '28daysAgo',
                    'end-date': 'today',
                    metrics: 'ga:sessions',
                    dimensions: 'ga:avgScreenviewDuration'
                  }}
                  container="avgScreenviewDuration-per-session-table"
                  options={{
                    sortAscending: false,
                    sortColumn: 1
                  }}
                />
              </>
            )}
          </Paper>
        </Grid>
      </Grid>
    </div>
  );
};

export default BusinessIntelligence;
