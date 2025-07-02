'use client'

import Image from 'next/image'
import default_pfp from '@/public/profile.png'
import { useEffect, useState } from "react";
import viewers_icon from '@/public/group.png'

interface props {
    username: string,
    title: string,
    category: string,
}

const StreamInfo = ({ title, category, username }: props) => {
    const [viewerCount, setViewerCount] = useState<number>(0);

    useEffect(() => {
        let eventSource: EventSource;
        let retryTimeout: NodeJS.Timeout;

        const connect = () => {
            eventSource = new EventSource(`http://localhost:8080/stream/sse/${username}`);

            const handler = (event: MessageEvent) => {
                try {
                    const count = parseInt(event.data);
                    if (!isNaN(count)) {
                        setViewerCount(count);
                    }
                } catch {

                }
            };

            eventSource.addEventListener("user_count", handler);

            eventSource.onerror = (err) => {
                eventSource.close();
                retryTimeout = setTimeout(connect, 5000);
            };
        };

        connect();

        return () => {
            clearTimeout(retryTimeout);
            if (eventSource) {
                eventSource.close();
            }
        };
    }, [username]);



    return (
        <section className='flex'>
            <div className='pt-3 pb-2 px-8 flex-center'>
                <Image src={default_pfp} alt={`${username}'s picture`} />
            </div>
            <div className='flex-3 flex flex-col'>
                <span className="text-lg">{username}</span>
                <span className="opacity-70">{title}</span>
                <span className="opacity-50">{category}</span>
            </div>
            <div className='flex-1 flex flex-col items-end text-white font-semibold justify-end px-4'>
                <button className='px-4 py-1 bg-main-purple hover:bg-hover-purple mb-2 rounded cursor-pointer'>
                    Zaobserwuj
                </button>
                <div className='flex-center gap-1'>
                    <Image src={viewers_icon} alt="Liczba widzów:" width={20}></Image>
                    <span className='text-xl text-main-green'>{viewerCount}</span> <span>oglądających</span>
                </div>

            </div>
        </section>
    );
};

export default StreamInfo;
