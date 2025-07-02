import OfflineProfile from "@/app/(root)/(profile)/components/offlineProfile";
import OnlineProfile from "@/app/(root)/(profile)/components/onlineProfile";


const UserPage = async ({params}: { params: Promise<{ username: string }> }) => {

    const { username } = await params


    // for now
    const status = "online";


    return status === "online" ?
        <OnlineProfile username={username}/>
        : <OfflineProfile username={username}/>
};

export default UserPage;