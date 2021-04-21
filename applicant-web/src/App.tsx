import React from 'react';
import ReactGA from 'react-ga';
import './app.scss';
import { BrowserRouter } from 'react-router-dom';
import { AppRouter } from './components/AppRouter/AppRouter';
import { environment } from './environments/environment';
import Footer from './components/Footer/Footer';

ReactGA.initialize(`${environment.analytics.measurement_id}`);
ReactGA.pageview(window.location.pathname + window.location.search);

const App: React.FC = () => (
  <BrowserRouter>
    <AppRouter />
    <Footer />
  </BrowserRouter>
);

export default App;
