'use client';

import { useEffect, useState } from 'react';
import Tmp_LiveButton from "@/app/components/tmp_LiveButton";

interface StreamResponse {
    url: string;
    streamKey: string;
}

interface User {
    id: number;
    username: string;
}

const StartStreamPage = () => {
    const [title, setTitle] = useState('');
    const [category, setCategory] = useState('');
    const [response, setResponse] = useState<StreamResponse | null>(null);
    const [token, setToken] = useState<string | null>(null);
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        const storedToken = typeof window !== "undefined" ? sessionStorage.getItem("token") : null;
        setToken(storedToken);
    }, []);

    useEffect(() => {
        if (!token) return;

        const fetchUser = async () => {
            const res = await fetch("http://localhost:8080/user/me", {
                method: "GET",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            });

            if (!res.ok) return;

            const data = await res.json();
            setUser({ id: data.id, username: data.username });
        };

        fetchUser();
    }, [token]);

    return (
        <div className="max-w-xl mx-auto mt-10 space-y-6 p-6 bg-main-bg border border-black rounded-xl shadow-xl">
            <h1 className="text-2xl font-bold">Tworzysz streama mordo</h1>

            <div className="flex flex-col gap-2">
                <label className="text-lg font-medium">Tytuł streama</label>
                <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="Wpisz tytuł"
                    className="p-2 border rounded bg-secondary-bg"
                />
            </div>

            <div className="flex flex-col gap-2">
                <label className="text-lg font-medium">Kategoria</label>
                <input
                    type="text"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                    placeholder="Wpisz kategorię"
                    className="p-2 border rounded bg-secondary-bg"
                />
            </div>

            {user && (
                <Tmp_LiveButton
                    title={title}
                    category={category}
                    userId={user.id}
                    username={user.username}
                    onSuccess={(data) => setResponse(data)}
                />
            )}

            {response && (
                <div className="mt-6 p-4 border-2 border-main-purple rounded-lg text-main-purple">
                    <p><strong>Stream wystartowany!</strong></p>
                    <p><strong>URL:</strong> <span className="break-words">{response.url}</span></p>
                    <p><strong>KEY:</strong> <span className="break-words">{response.streamKey}</span></p>
                </div>
            )}
        </div>
    );
};

export default StartStreamPage;
