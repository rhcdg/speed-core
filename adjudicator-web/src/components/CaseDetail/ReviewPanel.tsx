import React from 'react';
import {
  Grid,
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  DialogContentText,
  DialogActions,
  FadeProps
} from '@material-ui/core';
import { CaseDetailPanelProps, CaseStatus } from '../../utils/caseUtils';

const ReviewPanel: React.FC<CaseDetailPanelProps> = ({ kase, updateCase }) => {
  const [decision, setDecision] = React.useState<string>('');
  const [denyOpen, setDenyOpen] = React.useState<boolean>(false);
  const [approveOpen, setApproveOpen] = React.useState<boolean>(false);

  React.useEffect(() => {
    if (kase.decisionJustification) {
      setDecision(kase.decisionJustification);
    }
  }, [kase.decisionJustification]);

  const denyCase = () => {
    if (decision) {
      updateCase &&
        updateCase(
          {
            ...kase,
            decisionJustification: decision,
            status: CaseStatus.Rejected
          },
          true
        );
    } else {
      setDenyOpen(true);
    }
  };

  const approveCase = () => {
    if (
      decision &&
      kase.caseCorrespondenceValidated &&
      kase.supportingEvidenceValidated &&
      kase.securityCheckValidated
    ) {
      updateCase &&
        updateCase(
          {
            ...kase,
            decisionJustification: decision,
            status: CaseStatus.Validated
          },
          true
        );
    } else {
      setApproveOpen(true);
    }
  };

  return (
    <Grid container>
      <Grid container className="form-row">
        <label htmlFor="justification">Decision Justification</label>
        <textarea
          rows={10}
          className="text-area-justification"
          id="justification"
          name="justification"
          data-testid="justification-textarea"
          value={decision}
          onChange={e => setDecision(e.currentTarget.value)}
        />
      </Grid>
      <Grid container className="form-row buttons">
        <Button
          variant="contained"
          color="secondary"
          data-testid="deny-case"
          id="deny-case"
          onClick={denyCase}
        >
          Deny
        </Button>
        <Button
          variant="contained"
          color="primary"
          data-testid="approve-case"
          id="approve-case"
          onClick={approveCase}
        >
          Approve
        </Button>
      </Grid>
      <Dialog
        open={denyOpen}
        TransitionProps={{ role: 'presentation' } as FadeProps}
        aria-labelledby="deny-dialog-title"
        aria-describedby="deny-dialog-description"
      >
        <DialogTitle id="deny-dialog-title">Justification Needed</DialogTitle>
        <DialogContent>
          <DialogContentText id="deny-dialog-description">
            You need to provide a justification before denying.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => setDenyOpen(false)}
            data-testid="deny-dialog-confirm"
            color="primary"
            autoFocus
          >
            OK
          </Button>
        </DialogActions>
      </Dialog>
      <Dialog
        open={approveOpen}
        TransitionProps={{ role: 'presentation' } as FadeProps}
        aria-labelledby="approve-dialog-title"
        aria-describedby="approve-dialog-description"
      >
        <DialogTitle id="approve-dialog-title">Error</DialogTitle>
        <DialogContent>
          <div id="approve-dialog-description">
            Before approving you need to:
            <ul>
              {!decision && <li>Provide a justification</li>}
              {!kase.caseCorrespondenceValidated && (
                <li>Validate payment has been submitted</li>
              )}
              {!kase.supportingEvidenceValidated && (
                <li>Validate required supporting documents submitted</li>
              )}
              {!kase.securityCheckValidated && (
                <li>Validate security check results</li>
              )}
            </ul>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => setApproveOpen(false)}
            data-testid="approve-dialog-confirm"
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

export default ReviewPanel;
