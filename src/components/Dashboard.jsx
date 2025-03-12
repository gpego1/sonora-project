import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './styles/Dashboard.css';

function Dashboard() {
    const [userName, setUserName] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        console.log('Token no Dashboard:', token); // Adicione esta linha
        if (!token) {
            navigate('/login');
            return;
        }

        try {
            const decodedToken = JSON.parse(atob(token.split('.')[1]));
            setUserName(decodedToken.name || 'Usuário');
        } catch (error) {
            console.error('Erro ao decodificar o token:', error);
            setUserName('Usuário');
        }
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('userType');
        navigate('/login');
    };

    return (
        <div className="dashboard-container">
            <h2>Bem-vindo ao Dashboard, {userName}!</h2>
            <button onClick={handleLogout} className="logout-button">
                Finalizar Sessão
            </button>
            <div className="conteudo-dashboard">
                <p>Conteúdo do dashboard aqui...</p>
            </div>
        </div>
    );
}

export default Dashboard;