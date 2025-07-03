'use client';

import Image from 'next/image';
import logo from '@/public/logo.png';
import notifications from '@/public/notification.png';
import tokens from '@/public/tokens.png';
import pfp from '@/public/profile.png';
import SearchBar from "@/app/components/searchBar";
import Link from "next/link";
import { useEffect, useState } from "react";
import PedroCoinsManagement from "@/app/(root)/(profile)/components/pedroCoinsManagement";

interface User {
    id: number;
    username: string;
    email: string;
}

const Nav = () => {
    const [loggedIn, setLoggedIn] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const [token, setToken] = useState<string | null>(null);

    useEffect(() => {
        if (typeof window !== 'undefined') {
            const storedToken = sessionStorage.getItem("token");
            setToken(storedToken);
            setLoggedIn(!!storedToken);
        }
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
            setUser(data);
            console.log("USER:", data);
        };

        fetchUser();
    }, [token]);

    return (
        <nav className='flex-between fixed w-full px-6 py-4 lg:px-8 bg-secondary-bg h-18 z-50 border-b-2 border-b-black'>
            <Link href='/'>
                <div className='flex items-center gap-1'>
                    <Image src={logo} alt="Pedro Logo" width={64} height={32} />
                    <span className='text-4xl hidden md:block'>PEDRO</span>
                </div>
            </Link>

            <SearchBar />

            {loggedIn ? (
                <div className='flex gap-4 lg:gap-6 items-center'>
                    <PedroCoinsManagement />
                    <Image src={notifications} alt="Notifications" width={24} height={24} />
                    <Link className="cursor-pointer" href={`/${user?.username}`}>
                        <Image src={pfp} alt="Profile" width={48} height={48} />
                    </Link>
                    <Link className="border px-2 py-1 rounded-xl cursor-pointer" href="/start">
                        GO LIVE
                    </Link>
                </div>
            ) : (
                <div className='flex gap-4 items-center'>
                    <Link href='/login' className='bg-main-gray auth-btn'>
                        Zaloguj się
                    </Link>
                    <Link href='/register' className='bg-main-purple auth-btn hidden lg:block'>
                        Zarejestruj się
                    </Link>
                </div>
            )}
        </nav>
    );
};

export default Nav;
