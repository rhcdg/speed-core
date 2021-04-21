import React from 'react';

const MainPage: React.FC = () => {
  return (
    <div id="main-content" className="main-content">
      <div className="card">
        <div className="card-body">
          <p>
            You are accessing a U.S. Government (USG) Information System (IS)
            that is provided for USG-authorized use only.
          </p>

          <p>
            By using this IS (which includes any device attached to this IS),
            you consent to the following conditions:
          </p>

          <ul>
            <li>
              The USG routinely intercepts and monitors communications on this
              IS for purposes including, but not limited to, penetration
              testing, COMSEC monitoring, network operations and defense,
              personnel misconduct (PM), law enforcement (LE), and
              counter-intelligence (CI) investigations.
            </li>
            <li>
              At any time, the USG may inspect and seize data stored on this IS.
            </li>
            <li>
              Communications using, or data stored on, this IS are not private,
              are subject to routine monitoring, interception, and search, and
              may be disclosed or used for any USG authorized purpose.
            </li>
            <li>
              This IS includes security measures (e.g., authentication and
              access controls) to protect USG interests--not for your personal
              benefit or privacy.
            </li>
            <li>
              Notwithstanding the above, using this IS does not constitute
              consent to PM, LE or CI investigative searching or monitoring of
              the content of privileged communications, or work product, related
              to personal representation or services by attorneys,
              psychotherapists, or clergy, and their assistants. Such are
              private and confidential.
            </li>
            <li>
              Security protections may be utilized on this IS to protect certain
              interests that are important to the USG. These protections are not
              provided for your benefit or privacy and may be modified or
              eliminated at the USG&apos;s discretion. By signing this document,
              you acknowledge and consent that when you access Department of
              Defense (DoD) information systems, you are accessing a U.S.
              Government information system (as defined in CNSSI 4009) that is
              provided for U.S. Government-authorized use only.
            </li>
          </ul>

          <div className="center-content">
            <a className="btn btn-primary" href="/login">
              Accept and Login
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
