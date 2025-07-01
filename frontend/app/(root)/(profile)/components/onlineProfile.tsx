import StreamRoom from "@/app/(root)/(profile)/components/streamRoom";
import {notFound} from "next/navigation";
import NotFound from "@/app/not-found";

interface props {
    username: string;
}

const OnlineProfile = async ({username, }: props) => {

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
        <StreamRoom token={data}/>
    );


};

export default OnlineProfile;