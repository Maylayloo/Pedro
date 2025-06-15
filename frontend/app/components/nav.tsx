import Image from 'next/image'
import logo from '@/public/logo.png'
import notifications from '@/public/notification.png'
import tokens from '@/public/tokens.png'
import pfp from '@/public/profile.png'
import SearchBar from "@/app/components/searchBar";
import Link from "next/link";

const Nav = () => {

    // For now
    const loggedIn = true;

    return (
        <nav
            className='flex-between fixed w-full px-6 py-4 lg:px-8 bg-secondary-bg h-18 z-50 border-b-2 border-b-black'>
            <Link href='/'>
                <div className='flex items-center gap-1'>
                    <Image src={logo} alt="Pedro Logo" width={64} height={32}/>
                    <span className='text-4xl hidden md:block'>PEDRO</span>
                </div>
            </Link>

            <SearchBar/>
            {
                loggedIn ? (
                    <div className='flex gap-4 lg:gap-6 items-center'>
                        {/*There will be components in the future*/}
                        <Image src={tokens} alt="pedroCoins icon" width={42} height={42}/>
                        <Image src={notifications} alt="notifications icon" width={24} height={24}/>
                        <Image src={pfp} alt="notifications icon" width={48} height={48}/>
                    </div>
                ) : (
                    <div className='flex gap-4 items-center'>
                        <Link href='/login' className='bg-main-gray auth-btn'>
                            Zaloguj się
                        </Link>
                        <Link href='/register' className='bg-main-purple auth-btn hidden lg:block'>
                            Zarejestruj się
                        </Link>
                    </div>
                )

            }


        </nav>
    );
};

export default Nav;