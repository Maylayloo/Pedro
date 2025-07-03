import StreamRoom from "@/app/(root)/(profile)/components/streamRoom";

const OnlineProfile = async ({username, userId}: props) => {

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
    const token = await res.text()

    const res2 = await fetch(`http://localhost:8080/stream/${username}`, {
        method: "GET",
        credentials: "include",
        cache: "no-cache",
        headers: {
            "content-type": "application/json",
        }
    })

    const streamData = await res2.json()

    if (streamData) {
        console.log(streamData)
    } else {
        return <NotFound/>
    }

    console.log(streamData)

    if (!res.ok || !res2.ok) return <NotFound/>;


    return (
        <div className='flex flex-col lg:flex-row '>
            <div className='w-full lg:w-[70%] xl:w-[75%] 4xl:w-[80%]'>
                <StreamRoom token={token}/>
                <StreamInfo
                    username={streamData.roomName}
                    title={streamData.title}
                    category={streamData.category}
                    userId={userId}
                />
            </div>

            <Chat
                streamId={streamData.id}
            />
        </div>
    );


};

import NotFound from "@/app/not-found";
import Chat from "@/app/(root)/(profile)/components/chat";
import StreamInfo from "@/app/(root)/(profile)/components/streamInfo";

interface props {
    username: string,
    userId: number,
}

export default OnlineProfile;