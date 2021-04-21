import React from 'react';
import { Grid, makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles({
  mainContainer: {
    padding: '0px 30px 150px 30px'
  },
  text: {
    paddingTop: '24px'
  },
  boldText: {
    fontWeight: 600
  }
});

const Eligibility: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid container className={classes.mainContainer}>
      <Grid item xs={12}>
        <Typography variant="body1" className={classes.boldText}>
          If you are in the United States and seek an Advance Parole Document,
          you may apply if:
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (1) You have a pending application to adjust status, Form I-485, and
          you seek to travel abroad temporarily for “urgent humanitarian
          reasons” or in furtherance of a “significant public benefit,” which
          may include a personal or family emergency or bona fide business
          reasons.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (2)You have a pending application for Temporary Protected Status (TPS)
          (FormI-821), have been granted TPS, or have been granted T or U
          nonimmigrantstatus. Whether you are permitted to retain TPS upon your
          return will depend on whether you continue to meet the requirements
          for TPS. If you have TPS and leave and reenter the United States
          during the validity period of your Advance Parole Document, you will
          not break the continuous physical presence requirement for maintaining
          your TPS.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          Important: If you have a TPS or other application pending and you
          leave the United States on advance parole, you may miss important
          notices from USCIS regarding your application, including requests for
          additional evidence. If you do not respond timely to these notices,
          USCIS may deem your application abandoned and, in that event, you will
          not receive the benefit you seek. It is very important that you make
          appropriate arrangements to ensure that you do not miss any such
          important notices.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (3) You have been granted parole pursuant to INA section 212(d)(5),
          AND you seek to travel abroad temporarily for urgent humanitarian
          reasons or in furtherance of a significant public benefit.
          Humanitarian reasons include travel to obtain medical treatment,
          attend funeral services for a family member, or visit an ailing
          relative.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (4) USCIS or U.S. Immigration and Customs Enforcement (ICE) has
          deferred action in your case as a childhood arrival based on the
          guidelines described in the Secretary of Homeland Security’s
          memorandum issued on June 15, 2012 (“Deferred Action for Childhood
          Arrivals” (DACA)). USCIS may, in its discretion, grant advance parole
          if you are traveling outside the United States for educational
          purposes, employment purposes, or humanitarian purposes.
          <br />
          (a) Educational purposes include, but are not limited to, semester
          abroad programs or academic research;
          <br />
          (b) Employment purposes include, but are not limited to, overseas
          assignments, interviews, conferences, training, or meetings with
          clients; and
          <br />
          (c) Humanitarian purposes include, but are not limited to, travel to
          obtain medical treatment, attend funeral services for a family member,
          or visit an ailing relative.
        </Typography>
        <Typography variant="body1" className={classes.boldText}>
          NOTE: Travel for vacation is not a valid purpose. You must NOT file
          Form I-131 with your deferred action request or your package will be
          rejected and returned to you.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (5) USCIS has granted you IMMACT 90 or LIFE Act Family Unity Program
          benefits, AND you seek to travel abroad temporarily for urgent
          humanitarian reasons or in furtherance of a significant public
          benefit, which may include a personal or family emergency or bona fide
          business reasons.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (6) You have a pending application for temporary resident status
          pursuant to INA section 245A, and you seek to travel abroad
          temporarily for urgent humanitarian reasons or in furtherance of a
          significant public benefit, which may include a personal or family
          emergency or bona fide business reasons.
        </Typography>
        <Typography variant="body1" className={classes.text}>
          (7) You have been granted V nonimmigrant status in the United States,
          AND you seek to travel abroad temporarily for urgent humanitarian
          reasons or in furtherance of a significant public benefit, which may
          include a personal or family emergency or bona fide business reasons.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Eligibility;
