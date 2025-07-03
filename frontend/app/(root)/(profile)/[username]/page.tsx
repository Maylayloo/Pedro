import OnlineProfile from "@/app/(root)/(profile)/components/onlineProfile";
import OfflineProfile from "@/app/(root)/(profile)/components/offlineProfile";
import NotFound from "@/app/not-found";
const UserPage = async ({ params }: { params: Promise<{ username: string }> }) => {
    const { username } = await params;

    const res = await fetch(`http://localhost:8080/user/nick/${username}`, {
        method: "GET",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        }
    });

    if (res.status === 404) {
        return <NotFound />;
    }

    if (!res.ok) {
        console.error("Błąd HTTP:", res.status);
        return <NotFound />
    }

    const fetchedData = await res.json();

    if (!fetchedData) return <NotFound />;

    return fetchedData.streaming ? (
        <OnlineProfile username={username} userId={fetchedData.id} />
    ) : (
        <OfflineProfile username={username} userId={fetchedData.id} />
    );
};

export default UserPage;
