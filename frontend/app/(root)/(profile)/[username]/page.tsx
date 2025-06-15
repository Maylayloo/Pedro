import OfflineProfile from "@/app/(root)/(profile)/components/offlineProfile";
import OnlineProfile from "@/app/(root)/(profile)/components/onlineProfile";


interface props {
    params: {
        username: string;
    }
}

const UserPage = ({params}: props) => {

    // for now
    const status = "online";


    return status === "online" ?
        <OnlineProfile username={params.username}/>
        : <OfflineProfile username={params.username}/>
};

export default UserPage;