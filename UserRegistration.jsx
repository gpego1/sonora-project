import React, { useState } from 'react';
import axios from 'axios';
import './components/user-registration.css'; // Certifique-se de que o caminho está correto

function UserRegistration({ onLoginSuccess }) {
    const [isLogin, setIsLogin] = useState(true);
    const [userType, setUserType] = useState('customer');
    const [name, setName] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phone, setPhone] = useState('');
    const [errorMessage, setErrorMessage] = useState(null);

    const handleSubmit = (event) => {
        event.preventDefault();
        const userData = {
            userType: userType,
            name: name,
            cpf: cpf,
            email: email,
            password: password,
            phone: phone,
        };

        const authEndpoint = isLogin ? '/users/login' : '/users/register';

        axios.post(authEndpoint, userData, {
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (isLogin) {
                    if (response.data.token && response.data.name) {
                        localStorage.setItem('token', response.data.token);
                        localStorage.setItem('userName', response.data.name);
                        onLoginSuccess(response.data.name, response.data.token);
                        alert('Login realizado com sucesso!');
                    } else {
                        alert('Login realizado com sucesso, mas token ou nome não recebidos.');
                    }
                    setEmail('');
                    setPassword('');
                } else {
                    alert('Cadastro realizado com sucesso!');
                    setUserType('customer');
                    setName('');
                    setCpf('');
                    setEmail('');
                    setPassword('');
                    setPhone('');
                }
                setErrorMessage(null);
            })
            .catch((error) => {
                if (error.response && error.response.data) {
                    setErrorMessage(error.response.data);
                } else {
                    setErrorMessage(isLogin ? 'Erro ao realizar login.' : 'Erro ao realizar cadastro.');
                }
                console.error(
                    isLogin ? 'Erro ao realizar login:' : 'Erro ao realizar cadastro:',
                    error
                );
            });
    };

    return (
        <div className="user-registration-container">
            <div className="user-registration-card">
                <div className="user-registration-header">
                    <h2 className="user-registration-title">{isLogin ? 'Login' : 'Cadastro'}</h2>
                    <p className="user-registration-description">
                        {isLogin
                            ? 'Entre com suas credenciais para acessar sua conta'
                            : 'Preencha os dados abaixo para criar sua conta'}
                    </p>
                </div>
                {errorMessage && <p className="error-message">{errorMessage}</p>}
                <form onSubmit={handleSubmit} className="user-registration-form">
                    {!isLogin && (
                        <div className="form-group">
                            <label className="form-label">Tipo de Usuário</label>
                            <select
                                className="form-select"
                                value={userType}
                                onChange={(e) => setUserType(e.target.value)}
                            >
                                <option value="customer">Cliente</option>
                                <option value="artist">Artista</option>
                                <option value="host">Host</option>
                            </select>
                        </div>
                    )}

                    {!isLogin && (
                        <div className="form-group">
                            <label className="form-label">Nome</label>
                            <input
                                className="form-input"
                                type="text"
                                placeholder="Nome completo"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required={!isLogin}
                            />
                        </div>
                    )}

                    {!isLogin && (
                        <div className="form-group">
                            <label className="form-label">CPF</label>
                            <input
                                className="form-input"
                                type="text"
                                placeholder="000.000.000-00"
                                value={cpf}
                                onChange={(e) => setCpf(e.target.value)}
                                required={!isLogin}
                            />
                        </div>
                    )}

                    <div className="form-group">
                        <label className="form-label">Email</label>
                        <input
                            className="form-input"
                            type="email"
                            placeholder="seu@email.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">Senha</label>
                        <input
                            className="form-input"
                            type="password"
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    {!isLogin && (
                        <div className="form-group">
                            <label className="form-label">Telefone</label>
                            <input
                                className="form-input"
                                type="tel"
                                placeholder="(00) 00000-0000"
                                value={phone}
                                onChange={(e) => setPhone(e.target.value)}
                                required={!isLogin}
                            />
                        </div>
                    )}

                    <button type="submit" className="btn btn-primary btn-full">
                        {isLogin ? 'Entrar' : 'Cadastrar'}
                    </button>
                </form>

                <div className="user-registration-footer">
                    <button
                        className="btn btn-link"
                        onClick={() => setIsLogin(!isLogin)}
                    >
                        {isLogin ? 'Ir para Cadastro' : 'Ir para Login'}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default UserRegistration;