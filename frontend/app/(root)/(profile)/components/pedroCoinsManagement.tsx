'use client';

import Image from "next/image";
import tokens from "@/public/tokens.png";
import { useState, useRef, useEffect } from "react";

const PedroCoinsManagement = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [balance, setBalance] = useState<number | null>(null);
    const [token, setToken] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    const containerRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (typeof window !== 'undefined') {
            const t = sessionStorage.getItem("token");
            setToken(t);
        }
    }, []);

    const fetchBalance = async () => {
        if (!token) return;
        try {
            const res = await fetch("http://localhost:8080/user/me/coins", {
                headers: {
                    "Authorization": `Bearer ${token}`,
                },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.text();
            setBalance(JSON.parse(data));
        } catch (err) {
            console.error("Błąd pobierania salda:", err);
            setBalance(null);
        }
    };

    useEffect(() => {
        if (token) fetchBalance();
    }, [token]);

    useEffect(() => {
        const handleClickOutside = (e: MouseEvent) => {
            if (
                containerRef.current &&
                !containerRef.current.contains(e.target as Node)
            ) {
                setIsOpen(false);
            }
        };
        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    const handleToggle = () => {
        fetchBalance()
        setIsOpen(prev => !prev)
    };

    const handleTopUp = async () => {
        if (!token) return;

        setLoading(true);
        try {
            const res = await fetch("http://localhost:8080/user/refill", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                },
            });

            if (!res.ok) throw new Error(`Refill failed: HTTP ${res.status}`);
            await fetchBalance();
        } catch (err) {
            console.error("Błąd doładowania:", err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="relative shadow-xl" ref={containerRef}>
            <button onClick={handleToggle} className='cursor-pointer'>
                <Image src={tokens} alt="Pedro Coins" width={42} height={42} />
            </button>

            {isOpen && (
                <div className="absolute -right-5 top-6 mt-2 w-64 bg-main-bg border border-white rounded-lg shadow-lg p-4 z-50">
                    <p className="text-lg font-semibold mb-2">Stan konta:</p>
                    <p className="text-main-purple font-bold text-xl mb-4">
                        {balance !== null ? `${balance} PedroCoinów` : "Ładowanie..."}
                    </p>

                    <button
                        onClick={handleTopUp}
                        disabled={loading}
                        className={`w-full px-4 py-2 rounded-lg transition-colors cursor-pointer ${
                            loading
                                ? "bg-gray-500 cursor-not-allowed"
                                : "bg-main-green hover:bg-hover-green text-white"
                        }`}
                    >
                        {loading ? "Doładowywanie..." : "Doładuj PedroCoiny"}
                    </button>
                </div>
            )}
        </div>
    );
};

export default PedroCoinsManagement;
