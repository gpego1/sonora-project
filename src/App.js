import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import UserRegistration from './components/UserRegistration';
import Login from './components/Login';
import AuthRoute from './components/AuthRoute'; // Importe o AuthRoute

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<AuthRoute />} />
                <Route path="/register" element={<UserRegistration />} />
                <Route path="/login" element={<Login />} />
            </Routes>
        </Router>
    );
}

export default App;