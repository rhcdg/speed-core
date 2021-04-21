import React from 'react';
import {
  Grid,
  Typography,
  TableContainer,
  Paper,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  makeStyles
} from '@material-ui/core';

const useStyles = makeStyles({
  table: {
    minWidth: 650
  },
  tableLabel: {
    fontWeight: 500,
    height: '2rem',
    padding: '16px 0px 36px 0px'
  }
});

export interface FormTableRow {
  formNumber: string;
  submissionDate: string;
  submissionStatus: string;
}

interface HomeTableProps {
  tableType: string;
  rows: FormTableRow[];
}

const HomeTable: React.FC<HomeTableProps> = ({ tableType, rows }) => {
  const classes = useStyles();
  const titleText =
    tableType === 'submitted'
      ? 'Submitted Applications'
      : 'Applications In Progress';
  return (
    <Grid item xs={12} lg={6}>
      <label htmlFor="submitted-forms-table">
        <Typography className={classes.tableLabel}>{titleText}</Typography>
      </label>

      <TableContainer component={Paper}>
        <Table
          className={classes.table}
          id={`${tableType}-forms-table`}
          aria-label={`${tableType}-forms-table`}
        >
          <TableHead>
            <TableRow>
              <TableCell>Form</TableCell>
              <TableCell>Reference #</TableCell>
              <TableCell>Date Submitted</TableCell>
              <TableCell>Status</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.length ? (
              rows.map(row => (
                <TableRow key={row.formNumber}>
                  <TableCell>I-131</TableCell>
                  <TableCell>{row.formNumber}</TableCell>
                  <TableCell>{row.submissionDate || 'No date found'}</TableCell>
                  <TableCell>{row.submissionStatus}</TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell>No forms found</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Grid>
  );
};

export default HomeTable;
