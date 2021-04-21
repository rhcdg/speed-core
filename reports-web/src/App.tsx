import React from 'react';
import { BrowserRouter } from 'react-router-dom';

import './app.scss';

import { AppRouter } from './components/AppRouter/AppRouter';
import Footer from './components/Footer/Footer';

const App: React.FC = () => (
  <BrowserRouter>
    <AppRouter />
    <Footer />
  </BrowserRouter>
);

export default App;
