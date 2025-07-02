'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

const RegisterPage = () => {
    const [form, setForm] = useState({
        username: '',
        email: '',
        password: '',
    });
    const [error, setError] = useState('');
    const router = useRouter();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');

        try {
            const res = await fetch('http://localhost:8080/auth/register', {
                method: 'POST',
                credentials: 'include',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(form),
            });

            if (!res.ok) {
                const data = await res.json();
                throw new Error(data.message || 'Rejestracja nie powiodła się');
            }

            router.push('/login');
        } catch (err: any) {
            setError(err.message || 'Błąd serwera');
        }
    };

    return (
        <div className="max-w-md mx-auto mt-16 px-4 text-center">
            <h2 className="text-2xl font-bold mb-6">Rejestracja</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                <input
                    type="text"
                    name="username"
                    maxLength={16}
                    placeholder="Nazwa użytkownika"
                    className="w-full p-2 border rounded"
                    value={form.username}
                    onChange={handleChange}
                    required
                />

                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    className="w-full p-2 border rounded"
                    value={form.email}
                    onChange={handleChange}
                    required
                />

                <input
                    type="password"
                    name="password"
                    placeholder="Hasło"
                    className="w-full p-2 border rounded"
                    value={form.password}
                    onChange={handleChange}
                    required
                />

                {error && <p className="text-red-500 text-sm">{error}</p>}

                <button
                    type="submit"
                    className="bg-main-green text-white py-2 px-6 rounded-xl hover:bg-hover-green cursor-pointer"
                >
                    Zarejestruj się
                </button>
            </form>
        </div>
    );
};

export default RegisterPage;
