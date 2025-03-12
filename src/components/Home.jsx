import React from 'react';
import { useNavigate } from 'react-router-dom';
import './styles/Home.css';

function Home() {
    const navigate = useNavigate();

    const handleRegister = () => {
        navigate('/register');
    };

    const handleLogin = () => {
        navigate('/login');
    };

    return (
        <div className="home-container">
            <div className="welcome-message">
                <h1>Bem-vindo ao Sonora!</h1>
                <p>Cadastre-se ou faça login para continuar.</p>
                <div className="auth-buttons">
                    <button onClick={handleRegister} className="register-button">
                        Cadastrar
                    </button>
                    <button onClick={handleLogin} className="login-button">
                        Login
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Home;