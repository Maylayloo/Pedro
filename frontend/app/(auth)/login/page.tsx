'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

const LoginPage = () => {
    const [form, setForm] = useState({
        username: '',
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
            const res = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                credentials: 'include',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(form),
            });

            if (!res.ok) {
                const data = await res.json();
                throw new Error(data.message || 'Logowanie nie powiodło się');
            }

            const data = await res.json();

            if (data.token) {
                // for now, I did not have time :(
                sessionStorage.setItem('token', data.token);
            } else {
                throw new Error('NO TOKEN FOR YOU')
            }


            router.push('/');
        } catch (err: any) {
            setError(err.message || 'Błąd serwera');
        }
    };

    return (
        <div className="max-w-md mx-auto mt-16 px-4 text-center">
            <h2 className="text-2xl font-bold mb-6">Logowanie</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                <input
                    type="email"
                    name="username"
                    placeholder="Email"
                    className="w-full p-2 border rounded"
                    value={form.username}
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
                    className="bg-main-purple text-white py-2 px-6 rounded-xl hover:bg-hover-purple cursor-pointer"
                >
                    Zaloguj się
                </button>
            </form>
        </div>
    );
};

export default LoginPage;
