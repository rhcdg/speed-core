import React from 'react';
import { Grid, Link, makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingBottom: '24px'
  },
  bold: {
    fontWeight: 600
  }
});

const PrivacyNotice: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.text}>
          <span className={classes.bold}>AUTHORITIES: </span>The information
          requested on this application, and the associated evidence, is
          collected under the Immigration and Nationality Act sections 103,
          208(c)(1)(C), 211, 212(d)(5)(A), 215 and 8 CFR sections 211.1(a)(3-4),
          212.5, and 223.1-223.3.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          <span className={classes.bold}>PURPOSE: </span>The primary purpose for
          providing the requested information on this application is to apply
          for a Reentry Permit, Refugee Travel Document, or Advance Parole
          Document, to include urgent humanitarian reasons or in furtherance of
          a significant public benefit. DHS uses the information you provide to
          grant or deny the immigration benefit you are seeking.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          <span className={classes.bold}>DISCLOSURE: </span>The information you
          provide is voluntary. However, failure to provide the requested
          information, including your Social Security number (if applicable),
          and any requested evidence, may delay a final decision or result in
          denial of your application.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          <span className={classes.bold}>ROUTINE USES: </span>DHS may share the
          information you provide on this application and any additional
          requested evidence with other Federal, state, local, and foreign
          government agencies and authorized organizations. DHS follows approved
          routine uses described in the associated published system of records
          notices [DHS/USCIS-001 - Alien File, Index, and National File Tracking
          System and DHS/USCIS-007 - Benefits Information System] and the
          published privacy impact assessments [DHS/USCIS/PIA-003(b) Integrated
          Digitization Document Management Program, DHS/USCIS/PIA-016a Computer
          Linked Application Information Management System and Associated
          Systems, and DHS/USCIS/PIA-051 Case and Activity Management for
          International Operations] which you can find at&nbsp;
          <Link href="www.dhs.gov/privacy">www.dhs.gov/privacy</Link>. DHS may
          also share this information, as appropriate, for law enforcement
          purposes or in the interest of national security.
        </Typography>
        <Typography variant="body1" className={classes.bold}>
          Paperwork Reduction Act
        </Typography>
        <Typography variant="body1" className={classes.text}>
          An agency may not conduct or sponsor an information collection, and a
          person is not required to respond to a collection of information,
          unless it displays a currently valid Office of Management and Budget
          (OMB) control number. The public reporting burden for this collection
          of information is estimated at 1.90 hours per response, including the
          time for reviewing instructions, gathering the required documentation
          and information, completing the application, preparing statements,
          attaching necessary documentation, and submitting the application. The
          collection of biometrics is estimated to require 1.17 hours. The
          collection of passport-style photographs is estimated at 0.50 hours.
          Send comments regarding this burden estimate or any other aspect of
          this collection of information, including suggestions for reducing
          this burden, to: U.S. Citizenship and Immigration Services, Regulatory
          Coordination Division, Office of Policy and Strategy, 20 Massachusetts
          Ave NW, Washington, DC 20529-2140; OMB No .1615-0013.&nbsp;
          <span className={classes.bold}>
            Do not mail your completed Form I-131 to this address.
          </span>
        </Typography>
      </Grid>
    </Grid>
  );
};

export default PrivacyNotice;
