import React from 'react';
import Image from "next/image";
import logo_img from "@/public/logo.png";

const ChatLogo = () => {
    return (
        <div className='border-b-2 border-b-black text-center py-5 relative max-lg:border-t-2 max-lg:border-t-black '>
            <Image src={logo_img} alt="Pedro logo" width={64}
                   className='absolute left-0 top-1/2 -translate-y-1/2 rotate-[-30deg] opacity-50 lg:opacity-25'/>
            <span className='text-2xl'>
                    CZAT <span className='text-main-purple'>TRANSMISJI</span>
                </span>
            <Image src={logo_img} alt="Pedro logo" width={64}
                   className='absolute top-1/2 right-[20px] -translate-y-1/2 rotate-[30deg] opacity-50 lg:opacity-25'/>
        </div>
    );
};

export default ChatLogo;