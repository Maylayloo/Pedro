'use client'

import Link from "next/link";
import FollowingCard from "@/app/components/followingCard";
import Footer from "@/app/components/footer";
import {useEffect, useState} from "react";

const Sidebar = () => {
    interface following {
        nickname: string,
        pfp: string,
        status: boolean,
        category?: string,
        viewers?: number;
    }

    const [followingList, setFollowinglist] = useState<following[]>([]);
    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        const fetchFollowed = async () => {
            const token = typeof window !== "undefined" ? sessionStorage.getItem("token") : null;

            if (!token) {
                setLoggedIn(false);
                return;
            }

            try {
                const res = await fetch("http://localhost:8080/follow/myFollow", {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    }
                });

                if (!res.ok) {
                    console.error("Błąd pobierania follow listy:", res.status);
                    setLoggedIn(false);
                    return;
                }

                const data = await res.json();
                setFollowinglist(data);
                setLoggedIn(true);
                console.log(data)

            } catch (error) {
                console.error("Błąd sieci:", error);
                setLoggedIn(false);
            }
        };

        fetchFollowed();

        const handleFollowUpdated = () => {
            fetchFollowed();
        };

        window.addEventListener('follow-updated', handleFollowUpdated);

        return () => {
            window.removeEventListener('follow-updated', handleFollowUpdated);
        };
    }, []);

    return (
        <section className="sticky left-0 top-0 flex h-screen w-fit flex-col justify-between px-2 items-center
                 pt-24 text-white max-sm:hidden lg:w-[264px] bg-secondary-bg border-r-2 border-r-black">

            <span className='text-3xl mb-8'>OBSERWOWANI</span>

            {
                loggedIn ? (
                    <div className='flex flex-col flex-1 gap-6'>
                        {
                            followingList.length !== 0 ? (
                            followingList.map((following: following) => (
                                <FollowingCard
                                    key={following.nickname}
                                    name={following.nickname}
                                    status={following.status}
                                    pfp={following.pfp}
                                    category={following.category}
                                    viewers={following.viewers}
                                />
                            ))) : (
                                <span>Na razie jest tu cicho</span>
                            )
                        }

                        {/*<button className='cursor-pointer text-main-purple'>*/}
                        {/*    - - - - - - pokaż więcej - - - - - -*/}
                        {/*</button>*/}
                    </div>
                ) : (
                    <div className='flex flex-col flex-1 gap-6 items-center'>
                        <Link href='/login' className='bg-main-gray auth-btn'>
                            Zaloguj się
                        </Link>
                        <Link href='/register' className='bg-main-purple auth-btn hidden md:block'>
                            Zarejestruj się
                        </Link>
                    </div>
                )
            }

            <Footer />
        </section>
    );
};

export default Sidebar;
