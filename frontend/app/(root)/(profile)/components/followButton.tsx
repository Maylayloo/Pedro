'use client'

import Image from 'next/image'
import hearth from '@/public/hearth.png'
import {useEffect, useState} from "react";

interface props {
    username: string,
    userId: number
}

const FollowButton = ({username, userId}: props) => {
    const [alreadyFollowed, setAlreadyFollowed] = useState<boolean>(false)
    const [token, setToken] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true); // Do stanu ładowania

    useEffect(() => {
        if (typeof window !== "undefined") {
            const storedToken = sessionStorage.getItem("token");
            setToken(storedToken);
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            if (!token) {
                setIsLoading(false);
                return;
            }

            try {
                const res = await fetch(`http://localhost:8080/follow/myFollow/${username}`, {
                    method: 'GET',
                    credentials: "include",
                    headers: {
                        "content-type": "application/json",
                        "authorization": `Bearer ${token}`
                    },
                });

                if (!res.ok) {
                    console.error(res.status);
                    return;
                }

                const fetched = await res.json();

                if (fetched) {
                    setAlreadyFollowed(fetched.followed);
                }
            } catch (error) {
                console.error("Błąd podczas sprawdzania follow:", error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchData();
    }, [token]);

    const handleFollow = async () => {
        if (!token || isLoading) return;

        try {
            const res = await fetch(`http://localhost:8080/follow/${userId}`, {
                method: alreadyFollowed ? 'DELETE' : 'POST',
                credentials: "include",
                headers: {
                    "content-type": "application/json",
                    "authorization": `Bearer ${token}`
                },
            });

            if (!res.ok) {
                console.error("Błąd podczas follow/unfollow:", res.status);
                return;
            }

            setAlreadyFollowed(prev => !prev);
            window.dispatchEvent(new Event('follow-updated'));

        } catch (error) {
            console.error("Błąd sieci:", error);
        }
    };

    const isDisabled = isLoading || !token;

    return (
        <button
            className={`text-xl flex gap-2 px-4 py-2 rounded-lg transition-colors
                        ${isDisabled ? 'bg-main-gray cursor-not-allowed' : 'bg-main-purple hover:bg-hover-purple cursor-pointer'}`}
            onClick={handleFollow}
            disabled={isDisabled}
        >
            <span>
                {alreadyFollowed ? "Nie obserwuj" : "Zaobserwuj"}
            </span>
            <Image
                src={hearth}
                alt="hearth_icon"
                width={24}
                className={alreadyFollowed ? "hidden" : "block"}
            />
        </button>
    );
};

export default FollowButton;
