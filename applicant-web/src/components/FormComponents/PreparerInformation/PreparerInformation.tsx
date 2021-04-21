import React from 'react';
import { Link } from '@material-ui/core';
import InfoCard from '../../InfoCard/InfoCard';

const cardContents = (
  <div>
    NOTE: Do not file Form I-131 if you are seeking release from immigration
    custody and you want to remain in the United States as a parolee. You should
    contact ICE about your request at{' '}
    <Link href="www.ice.gov/contact/ero">www.ice.gov/contact/ero</Link>
  </div>
);

const PreparerInformation: React.FC = () => {
  return (
    <div>
      <InfoCard contents={cardContents} parent="form" />
      Preparer Information
    </div>
  );
};

export default PreparerInformation;
