'use client'

import ChatLogo from "@/app/(root)/(profile)/components/chatLogo";
import SockJS from "sockjs-client";
import {IFrame, Stomp} from "@stomp/stompjs";
import React, {useEffect, useRef, useState} from "react";


interface props {
    streamId: number,
}

interface ChatMessage {
    senderNickname: string,
    content: string,
    message: string,
    amount: number,
    type: "chat" | "donate",
    // timestamp: string;
}

const Chat = ({streamId}: props) => {
    const [token, setToken] = useState<string | null>(null);
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [messageType, setMessageType] = useState<string>("chat")
    const [input, setInput] = useState("");
    const stompClientRef = useRef<any>(null);
    const messagesEndRef = useRef<HTMLDivElement | null>(null);

    const [isDonate, setIsDonate] = useState(false)
    const [amount, setAmount] = useState<number>(0);
    const [gatheredAmount, setGatheredAmount] = useState<number>(0);

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

        const fetchData = async () => {
            const res = await fetch(`http://localhost:8080/stream/totalAmount/${streamId}`, {
                method: "GET",
                credentials: "include",
                headers: {
                    "content-type": "application/json",
                    "authorization": `Bearer ${token}`
                }

            })

            if (!res.ok) {
                return
            }

            const data = await res.text();

            if (data) {
                console.log("DFAATATATAATATATA")
                console.log(data)
                setGatheredAmount(JSON.parse(data))
            }

        }

        fetchData()
    }, [token])


    useEffect(() => {
        if (!token) return;

        const socket = new SockJS(`http://localhost:8080/chat?token=${token}`);
        const stompClient = Stomp.over(socket);
        stompClientRef.current = stompClient;

        stompClient.connect({}, function (frame: IFrame) {
            console.log("Połączono: " + frame);

            stompClient.subscribe(`/topic/chat/${streamId}`, function (message) {
                const response = JSON.parse(message.body);

                if (response.type === "donate" && typeof response.totalAmount === "number") {
                    console.log("audio")
                    const audio = new Audio("/donate.mp3")
                    audio.play().catch(err => console.warn("Nie można odtworzyć dźwięku:", err));

                    setGatheredAmount(response.totalAmount);
                }

                setMessages((prev) => [...prev, response]);
            });
        });

        return () => {
            stompClient.disconnect(() => console.log("Rozłączono"));
        };
    }, [streamId, token]);

    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({behavior: "smooth"});
    }, [messages]);

    const sendMessage = () => {
        setMessageType("chat")

        if (input.trim() && stompClientRef.current) {
            stompClientRef.current.send(
                `/app/chat/${streamId}`,
                {},
                JSON.stringify({type: "chat", message: input})
            );
            setInput("");
        }

    };

    const sendDonate = () => {
        setIsDonate(prev => !prev)


        if (input.trim() && stompClientRef.current && isDonate && amount !== 0) {
            console.log("POSZEDŁ DONATE")
            stompClientRef.current.send(
                `/app/chat/${streamId}`,
                {},
                JSON.stringify({type: "donate", message: input, amount: amount})
            )
        }
        setInput("");
    };

    return (
        <section
            className="flex flex-col lg:border-l-black lg:border-l-2 bg-secondary-bg flex-1 h-[calc(100vh-4.5rem)]">
            <ChatLogo/>

            <div className="flex-center p-1 border-b-2 border-b-black">
                <span className='text-lg'>
                    Zebrano już <span className='text-main-green font-bold'>{gatheredAmount}</span> Pedro Coinów!
                </span>
            </div>

            <div className="flex-4 overflow-y-auto p-4 space-y-1">
                {messages.map((msg: ChatMessage, index: number) => (
                    <div key={index}>
                        {
                            msg.type === "chat" ? (
                                <span>
                                    <span style={{color: getSenderColor(msg.senderNickname)}}>
                            {msg.senderNickname}
                        </span>: {msg.content}
                                </span>

                            ) : (
                                <div className='py-3 px-2 flex flex-col items-center bg-main-purple gap-1 rounded-lg'>
                                    <span className='text-xl'>{msg.senderNickname}</span>
                                    <div className='w-[60%] h-[1px] bg-white'/>
                                    <span>{msg.message}</span>
                                    <div className='w-[60%] h-[1px] bg-white'/>
                                    <span className='text-lg'>{msg.amount} PedroCoin</span>
                                </div>
                            )
                        }

                    </div>
                ))}
                <div ref={messagesEndRef}></div>
            </div>

            <div className="flex-1 border-t-2 border-t-black p-2 flex flex-col items-center">
                <input
                    type="number"
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                        setAmount(Number(e.target.value));
                    }}
                    className={`border rounded w-[80%] mb-2 px-4 py-2 ${isDonate ? "block" : "hidden"}`}
                    placeholder="Liczba Pedro Coinów.."
                />
                <textarea
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    className="w-full p-2 border border-gray-300 rounded resize-none overflow-hidden leading-relaxed"
                    rows={1}
                    placeholder={!isDonate ? "Zacznij rozmawiać..." : "Wprowadź treść dotacji..."}
                    onInput={(e) => {
                        const target = e.target as HTMLTextAreaElement;
                        target.style.height = "auto";
                        target.style.height = `${Math.min(target.scrollHeight, 3 * 24)}px`;
                    }}
                />
                <div className="flex-center mt-2 4xl:mt-6 gap-4 4xl:gap-8">
                    <button
                        className="bg-blue-800 px-5 py-2 rounded-xl bg-main-green cursor-pointer hover:bg-hover-green shadow-lg"
                        onClick={sendDonate}
                    >
                        {!isDonate ? "Dotacja" : "Wyślij żebrakowi"}
                    </button>
                    <button
                        className={`bg-blue-800 px-5 py-2 rounded-xl bg-main-purple cursor-pointer hover:bg-hover-purple shadow-lg ${isDonate ? 'hidden' : 'block'}`}
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
