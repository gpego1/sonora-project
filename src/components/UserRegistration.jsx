import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './styles/UserRegistration.css';

function UserRegistration() {
    const [formData, setFormData] = useState({
        name: '',
        cpf: '',
        email: '',
        phone: '',
        password: '',
        userType: 'customer',
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(
                `${process.env.REACT_APP_API_URL}/register`,
                formData
            );
            console.log('Cadastro bem-sucedido:', response.data);
            alert('Cadastro bem sucedido');
            navigate('/login'); // Redireciona para a página de login
        } catch (error) {
            console.error(
                'Erro durante o cadastro:',
                error.response ? error.response.data : error.message
            );
            alert(
                'Erro durante o cadastro:' +
                (error.response ? error.response.data : error.message)
            );
        }
    };

    return (
        <div className="register-container">
            <h2>Cadastro de Usuário</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="name">Nome:</label>
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="cpf">CPF:</label>
                    <input
                        type="text"
                        name="cpf"
                        value={formData.cpf}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="phone">Telefone:</label>
                    <input
                        type="text"
                        name="phone"
                        value={formData.phone}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="password">Senha:</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="userType">Tipo de Usuário:</label>
                    <select
                        name="userType"
                        value={formData.userType}
                        onChange={handleChange}
                    >
                        <option value="customer">Cliente</option>
                        <option value="artist">Artista</option>
                        <option value="host">Host</option>
                    </select>
                </div>
                <button type="submit">Cadastrar</button>
            </form>
        </div>
    );
}

export default UserRegistration;