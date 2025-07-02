'use client'
// EVERYTHING HERE IS TEMPORARY

const Tmp_LiveButton = () => {

    const tmp_showKey = async () => {
        const key_data = await fetch("http://localhost:8080/stream", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: "Stream testowy",
                    description: "Testowy opis testowego streama",
                    roomName: "Knight34",
                    category: "Testowa kategoria testowego Streama",
                    userId: 777
                })
            }
        )

        const fetched_key_data = await key_data.json()

        if (fetched_key_data) {
            console.log(fetched_key_data)
        }

    }

    return (
        <button className='border px-2 py-1 rounded-xl cursor-pointer' onClick={tmp_showKey}>
            GO LIVE
        </button>
    );
};

export default Tmp_LiveButton;