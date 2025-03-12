import React from 'react';
import { Navigate } from 'react-router-dom';
import Dashboard from './Dashboard';
import Home from './Home';

function AuthRoute() {
  const token = localStorage.getItem('token');

  if (token) {
    return <Dashboard />;
  } else {
    return <Home />;
  }
}

export default AuthRoute;