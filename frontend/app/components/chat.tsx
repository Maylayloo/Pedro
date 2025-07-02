'use client'

import ChatLogo from "@/app/(root)/(profile)/components/chatLogo";
import SockJS from "sockjs-client";
import { IFrame, Stomp } from "@stomp/stompjs";
import { useEffect, useRef, useState } from "react";

interface Props {
    streamId: number;
}

interface ChatMessage {
    senderNickname: string;
    content: string;
    // timestamp: string;
}

const Chat = ({ streamId }: Props) => {
    const [token, setToken] = useState<string | null>(null);
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [input, setInput] = useState("");
    const stompClientRef = useRef<any>(null);
    const messagesEndRef = useRef<HTMLDivElement | null>(null);

    const colors: string[] = [
        "#FF9808", "#14FF82", "#ED5AD9", "#1AEAD8", "#FA2A2A", "#2B95FF", "#F2FF3A", "#fa7dd6", "#5d30f0", "#b0eb28"
    ];

    const senderColorsRef = useRef<Map<string, string>>(new Map());

    const getSenderColor = (senderNickname: string) => {
        if (!senderColorsRef.current.has(senderNickname)) {
            const color = colors[Math.floor(Math.random() * colors.length)];
            senderColorsRef.current.set(senderNickname, color);
        }
        return senderColorsRef.current.get(senderNickname)!;
    };

    useEffect(() => {
        if (typeof window !== "undefined") {
            const storedToken = sessionStorage.getItem("token");
            setToken(storedToken);
        }
    }, []);

    useEffect(() => {
        if (!token) return;

        const socket = new SockJS(`http://localhost:8080/chat?token=${token}`);
        const stompClient = Stomp.over(socket);
        stompClientRef.current = stompClient;

        stompClient.connect({}, function (frame: IFrame) {
            console.log("Połączono: " + frame);

            stompClient.subscribe(`/topic/chat/${streamId}`, function (message) {
                const response = JSON.parse(message.body);
                setMessages((prev) => [...prev, response]);
            });
        });

        return () => {
            stompClient.disconnect(() => console.log("Rozłączono"));
        };
    }, [streamId, token]);

    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    const sendMessage = () => {
        if (input.trim() && stompClientRef.current) {
            stompClientRef.current.send(
                `/app/chat/${streamId}`,
                {},
                JSON.stringify({ message: input })
            );
            setInput("");
        }
    };

    return (
        <section className="flex flex-col lg:border-l-black lg:border-l-2 bg-secondary-bg flex-1 h-[calc(100vh-4.5rem)]">
            <ChatLogo />

            <div className="flex-4 overflow-y-auto p-4 space-y-1">
                {messages.map((msg: ChatMessage, index: number) => (
                    <div key={index}>
                        <span style={{ color: getSenderColor(msg.senderNickname) }}>
                            {msg.senderNickname}
                        </span>: {msg.content}
                    </div>
                ))}
                <div ref={messagesEndRef}></div>
            </div>

            <div className="flex-1 border-t-2 border-t-black p-2">
                <textarea
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    className="w-full p-2 border border-gray-300 rounded resize-none overflow-hidden leading-relaxed"
                    rows={1}
                    placeholder="Zacznij rozmawiać..."
                    onInput={(e) => {
                        const target = e.target as HTMLTextAreaElement;
                        target.style.height = "auto";
                        target.style.height = `${Math.min(target.scrollHeight, 3 * 24)}px`;
                    }}
                />
                <div className="flex-center mt-2 4xl:mt-6 gap-4 4xl:gap-8">
                    <button className="bg-blue-800 px-5 py-2 rounded-xl bg-main-green cursor-pointer hover:bg-hover-green shadow-lg">
                        Dotacja
                    </button>
                    <button
                        className="bg-blue-800 px-5 py-2 rounded-xl bg-main-purple cursor-pointer hover:bg-hover-purple shadow-lg"
                        onClick={sendMessage}
                    >
                        Rozmawiaj
                    </button>
                </div>
            </div>
        </section>
    );
};

export default Chat;
