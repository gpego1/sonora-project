import React from 'react';
import { Link } from 'react-router-dom';
import './components/Home.css';

function Home({ isLoggedIn, userName, onLogout }) {
    return (
        <div className="home-container">
            <header className="home-header">
                <div className="auth-buttons">
                    {isLoggedIn ? (
                        <>
                            <span className="welcome-message">Olá, {userName}</span>
                            <button className="logout-button" onClick={onLogout}>Logout</button>
                        </>
                    ) : (
                        <>
                            <Link to="/register" className="register-button">Registrar</Link>
                            <Link to="/login" className="login-button">Login</Link>
                        </>
                    )}
                </div>
            </header>
            <main className="home-content">
                <h1>Bem-vindo ao Sonora!</h1>
                <p>A plataforma de eventos musicais que conecta artistas, hosts e clientes.</p>
            </main>
        </div>
    );
}

export default Home;