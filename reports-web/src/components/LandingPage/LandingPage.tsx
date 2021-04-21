import React from 'react';
import { Tab, Tabs, Box, Paper, Button, Grid } from '@material-ui/core';
import BusinessIntelligence from '../Panels/BusinessIntelligence/BusinessIntelligence';

interface TabPanelProps {
  // eslint-disable-next-line react/require-default-props
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box p={3}>{children}</Box>}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`
  };
}

const LandingPage: React.FC = () => {
  const [value, setValue] = React.useState(0);

  // eslint-disable-next-line @typescript-eslint/ban-types
  const handleChange = (event: React.ChangeEvent<{}>, newValue: number) => {
    setValue(newValue);
  };

  return (
    <div id="main-content" className="main-content">
      <Paper>
        <Tabs value={value} onChange={handleChange} aria-label="Reporting Tabs">
          <Tab label="Business Intelligence" {...a11yProps(0)} />
          <Tab label="Submission & Failures" {...a11yProps(1)} />
          <Tab label="Processing Times" {...a11yProps(2)} />
          <Tab label="Additional Reports" {...a11yProps(3)} />
        </Tabs>
        <TabPanel value={value} index={0}>
          <BusinessIntelligence />
        </TabPanel>
        <TabPanel value={value} index={1}>
          Submission & Failures
        </TabPanel>
        <TabPanel value={value} index={2}>
          Processing Times
        </TabPanel>
        <TabPanel value={value} index={3}>
          <div>
            <Grid container spacing={3}>
              <Grid item xs={12} sm={6}>
                <Button
                  href="https://splunk.speedc2c24.steampunkfoundry.net/"
                  color="primary"
                >
                  Ad-Hoc Reports
                </Button>
              </Grid>
            </Grid>
          </div>
        </TabPanel>
      </Paper>
    </div>
  );
};

export default LandingPage;
