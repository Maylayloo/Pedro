'use client';

import React, { useState, useEffect, useRef } from 'react';
import {
    LiveKitRoom,
    RoomAudioRenderer,
    useTracks,
} from '@livekit/components-react';
import { Track } from 'livekit-client';

interface Props {
    token: string;
}

const VideoRenderer = ({ track }: { track: Track }) => {
    const ref = useRef<HTMLVideoElement>(null);

    useEffect(() => {
        const videoEl = ref.current;

        if (videoEl && track.kind === 'video') {
            track.attach(videoEl);
            return () => {
                track.detach(videoEl);
            };
        }
    }, [track]);


    return (
        <video
            ref={ref}
            autoPlay
            playsInline
            muted
            className="w-full h-full object-cover rounded-xl shadow-lg z-99 border p-1"
        />
    );
};

// Viewer dla wszystkich subskrybowanych tracków video
const StreamViewer = () => {
    const tracks = useTracks(undefined, { onlySubscribed: true });

    return (
        <div className="flex flex-wrap justify-center items-center w-full h-full gap-4 p-4">
            {tracks.map(({ publication }, index) => {
                const track = publication?.track;
                if (!track || track.kind !== 'video') {
                    console.log("BRAK VIDEO")
                    console.log(tracks)
                    return null

                };

                return (
                    <div key={index} className="w-[300px] h-[200px] bg-black rounded-xl overflow-hidden">
                        <VideoRenderer track={track} />
                    </div>
                );
            })}
        </div>
    );
};

// Główny komponent pokoju
const StreamRoom = ({ token }: Props) => {
    const [joined, setJoined] = useState(false);
    const serverURL = 'wss://livekit.pedro.com.pl';

    const handleJoin = () => {
        setJoined(true);
    };

    return (
        <div className="w-full h-screen flex justify-center items-center bg-gray-900 text-white">
            {!joined ? (
                <button
                    onClick={handleJoin}
                    className="px-6 py-3 bg-blue-600 hover:bg-blue-700 transition rounded-lg text-lg font-medium"
                >
                    Start stream
                </button>
            ) : (
                <LiveKitRoom
                    token={token}
                    serverUrl={serverURL}
                    connect={true}
                    video={false}
                    audio={false}
                    style={{ height: '100%', width: '100%' }}
                >
                    <RoomAudioRenderer />
                    siogma
                    <StreamViewer />
                    om
                </LiveKitRoom>
            )}
        </div>
    );
};

export default StreamRoom;
