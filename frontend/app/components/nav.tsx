import Image from 'next/image'
import logo from '@/public/logo.png'

const Nav = () => {
    return (
        <nav className='flex-between fixed w-full px-6 py-4 lg:px-8 bg-secondary-bg h-18 z-50 border-b-2 border-b-black'>
            <div className='flex items-center gap-1'>
                <Image src={logo} alt="Pedro Logo" width={64} height={32}/>
                <span className='text-4xl'>PEDRO</span>
            </div>
        </nav>
    );
};

export default Nav;