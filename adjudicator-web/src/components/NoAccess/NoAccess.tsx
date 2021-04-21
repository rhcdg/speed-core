import React from 'react';

const NoAccess: React.FC = () => {
  return (
    <div id="main-content" className="main-content">
      <div className="card">
        <div className="card-body">
          <p>You do not have access to the Case Management Portal.</p>
        </div>
      </div>
    </div>
  );
};

export default NoAccess;
