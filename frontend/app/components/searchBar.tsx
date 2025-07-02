import search from "@/public/search.png"
import Image from 'next/image'

const SearchBar = () => {
    return (
        <div className='flex border-2 border-white rounded-xl lg:w-[30%]'>
            <div className='flex items-center flex-1'>
                <input type="text" placeholder="Wyszukaj..." className='focus:outline-none w-full px-4 py-2 rounded-l-xl'/>
            </div>
            <button className='border-l-2 border-l-white p-2 cursor-pointer'>
                <Image src={search} alt='search icon' width={24} height={24}/>
            </button>
        </div>
    );
};

export default SearchBar;