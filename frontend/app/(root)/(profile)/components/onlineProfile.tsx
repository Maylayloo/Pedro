import StreamRoom from "@/app/(root)/(profile)/components/streamRoom";
import {notFound} from "next/navigation";

const OnlineProfile = async ({username,}: props) => {

    // Only guests for now
    const identity = `guest-${Date.now()}`;
    const res = await fetch("http://localhost:8080/getToken", {
        method: "POST",
        credentials: "include",
        cache: "no-cache",
        headers: {
            "content-type": "application/json",
        },
        body: JSON.stringify({
            identity: identity,
            name: identity,
            roomName: username
        })
    })
    const data = await res.text()

    if (!res.ok) return <NotFound/>;

    return (
        <div className='flex flex-col lg:flex-row'>
            <div className='w-full lg:w-[70%] xl:w-[80%]'>
                <StreamRoom token={data}/>
            </div>

            <Chat/>
        </div>
    );


};

import NotFound from "@/app/not-found";
import Chat from "@/app/components/chat";

interface props {
    username: string;
}

export default OnlineProfile;