'use client';

interface Props {
    title: string;
    category: string;
    userId: number,
    username: string,
    onSuccess: (data: { url: string; streamKey: string }) => void;
}

const Tmp_LiveButton = ({ title, category, onSuccess, userId, username}: Props) => {
    const tmp_showKey = async () => {
        try {
            const res = await fetch("http://localhost:8080/stream", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title,
                    description: "tak",
                    roomName: username,
                    category,
                    userId,
                })
            });

            if (!res.ok) {
                console.warn("Błąd przy tworzeniu streama");
                return;
            }

            const data = await res.json();

            if (data?.url && data?.streamKey) {
                onSuccess({ url: data.url, streamKey: data.streamKey });
            } else {
                console.warn("Brakuje klucza lub URL-a w odpowiedzi");
            }
        } catch (err) {
            console.error("Błąd połączenia:", err);
        }
    };

    return (
        <button className="border px-2 py-1 rounded-xl cursor-pointer" onClick={tmp_showKey}>
            GO LIVE
        </button>
    );
};

export default Tmp_LiveButton;
