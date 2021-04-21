import React, { ReactElement } from 'react';
import { cleanup, render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import CaseDetail from '../../components/CaseDetail/CaseDetail';
import { kase } from '../utils/caseData';

const mockUpdateAllCases = jest.fn();

const renderWithRouter = (ui: ReactElement) => {
  return render(
    <MemoryRouter initialEntries={[{ pathname: '/cases/4' }]}>
      {ui}
    </MemoryRouter>
  );
};

describe('case detail', () => {
  afterEach(cleanup);

  it('renders and shows case details', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const caseNumber = screen.getByText('Case 2021ABC123456789');
    expect(caseNumber).toBeInTheDocument();
  });
});

describe('active panel links', () => {
  afterEach(cleanup);

  it('defaults to Application Information as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Application Information');
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Application Type as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Application Type');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Travel Information as the active link', async () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Travel Information');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Signature as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Signature');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Supporting Evidence as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Supporting Evidence');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Case Correspondence as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Case Correspondence');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Case History as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Case History');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Security Check Results as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Security Check Results');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });

  it('defaults to Review & Submit as the active link', () => {
    renderWithRouter(
      <CaseDetail
        kase={kase}
        prevRoute="new"
        updateAllCases={mockUpdateAllCases}
      />
    );
    const link = screen.getByText('Review & Submit');
    fireEvent.click(link, new MouseEvent('click', { bubbles: true }));
    expect(link.classList.contains('active')).toBe(true);
  });
});
