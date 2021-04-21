import React, { ReactElement } from 'react';
import { cleanup, fireEvent, render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import InstructionPage from '../../components/InstructionPage/InstructionPage';
import { INSTRUCTIONS_URL } from '../../utils/routeUrls';

const renderWithRouter = (ui: ReactElement, { route = '/' } = {}) => {
  window.history.pushState({}, 'test page', route);

  return render(ui, { wrapper: BrowserRouter });
};

describe('instruction page', () => {
  afterEach(cleanup);

  it('renders without error', () => {
    renderWithRouter(<InstructionPage />, { route: INSTRUCTIONS_URL });
    const strongElement = screen.getByText('Instructions');
    expect(strongElement).toBeInTheDocument();
  });

  it('tests forward button', () => {
    renderWithRouter(<InstructionPage />, { route: INSTRUCTIONS_URL });
    const button = screen.getByText('Next');

    fireEvent.click(button, new MouseEvent('click'));

    expect(window.location.pathname).toEqual(`${INSTRUCTIONS_URL}/eligibility`);
  });

  it('tests back button', () => {
    renderWithRouter(<InstructionPage />, {
      route: `${INSTRUCTIONS_URL}/decision`
    });
    const button = screen.getByText('Previous');

    fireEvent.click(button, new MouseEvent('click'));

    expect(window.location.pathname).toEqual(
      `${INSTRUCTIONS_URL}/privacy-notice`
    );
  });

  describe('left nav links marked active', () => {
    it('overview', () => {
      renderWithRouter(<InstructionPage />, { route: INSTRUCTIONS_URL });
      const link = screen.getByText('Overview');

      expect(link.classList.contains('active')).toBe(true);
    });

    it('eligibility', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/eligibility`
      });
      const eligibilityLink = screen.getByText('Eligibility');
      const overviewLink = screen.getByText('Overview');

      expect(eligibilityLink.classList.contains('active')).toBe(true);
      expect(overviewLink.classList.contains('active')).toBe(true);
    });

    it('travel warning', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/travel-warning`
      });
      const link = screen.getByText('Travel Warning');
      const overviewLink = screen.getByText('Overview');

      expect(link.classList.contains('active')).toBe(true);
      expect(overviewLink.classList.contains('active')).toBe(true);
    });

    it('exceptions', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/exceptions`
      });
      const link = screen.getByText('Exceptions');
      const overviewLink = screen.getByText('Overview');

      expect(link.classList.contains('active')).toBe(true);
      expect(overviewLink.classList.contains('active')).toBe(true);
    });

    it('general instructions', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/general-instructions`
      });
      const link = screen.getByText('General Instructions');

      expect(link.classList.contains('active')).toBe(true);
    });

    it('requirements', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/requirements`
      });
      const link = screen.getByText('Requirements');

      expect(link.classList.contains('active')).toBe(true);
    });

    it('initial evidence', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/initial-evidence`
      });
      const link = screen.getByText('Initial Evidence');
      const requirementsLink = screen.getByText('Requirements');

      expect(link.classList.contains('active')).toBe(true);
      expect(requirementsLink.classList.contains('active')).toBe(true);
    });

    it('photographs', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/photographs`
      });
      const link = screen.getByText('Photographs');
      const requirementsLink = screen.getByText('Requirements');

      expect(link.classList.contains('active')).toBe(true);
      expect(requirementsLink.classList.contains('active')).toBe(true);
    });

    it('biometrics services', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/biometrics-services`
      });
      const link = screen.getByText('Biometrics Services');
      const requirementsLink = screen.getByText('Requirements');

      expect(link.classList.contains('active')).toBe(true);
      expect(requirementsLink.classList.contains('active')).toBe(true);
    });

    it('filing fees', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/filing-fees`
      });
      const link = screen.getByText('Filing Fees');

      expect(link.classList.contains('active')).toBe(true);
    });

    it('accepted payment', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/accepted-payment`
      });
      const link = screen.getByText('Accepted Payment');
      const feesLink = screen.getByText('Filing Fees');

      expect(link.classList.contains('active')).toBe(true);
      expect(feesLink.classList.contains('active')).toBe(true);
    });

    it('processing information', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/processing-information`
      });
      const link = screen.getByText('Processing Information');

      expect(link.classList.contains('active')).toBe(true);
    });

    it('expedited request', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/expedited-request`
      });
      const link = screen.getByText('Expedited Request');
      const parentLink = screen.getByText('Processing Information');

      expect(link.classList.contains('active')).toBe(true);
      expect(parentLink.classList.contains('active')).toBe(true);
    });

    it('penalties', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/penalties`
      });
      const link = screen.getByText('Penalties');

      expect(link.classList.contains('active')).toBe(true);
    });

    it('privacy notice', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/privacy-notice`
      });
      const link = screen.getByText('Privacy Notice');

      expect(link.classList.contains('active')).toBe(true);
    });

    it('decision', () => {
      renderWithRouter(<InstructionPage />, {
        route: `${INSTRUCTIONS_URL}/decision`
      });
      const link = screen.getByText('Decision');

      expect(link.classList.contains('active')).toBe(true);
    });
  });
});
