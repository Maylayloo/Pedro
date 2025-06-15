import Image from 'next/image'
import default_pfp from '@/public/profile.png'
import Link from "next/link";

interface props {
    name: string,
    pfp?: string,
    status: "Online" | "Offline",
    category?: string,
    viewers?: number;
}

const FollowingCard = ({name, pfp, status, category, viewers}: props) => {

    const truncatedCategory: string | undefined = category && category.length > 12 ? category.slice(0, 12) + "..." : category;
    const truncatedName: string = name.length > 10 ? name.slice(0, 10) + "..." : name;

    return (
        <div className='flex gap-2 items-center'>
            <div>
                <Link href={`/${name}`}>
                    <Image src={pfp || default_pfp} alt={`${name}\`s profile picture`} width={32} height={32}
                           className={`${status === "Offline" && 'grayscale'}`}/>
                </Link>

            </div>

            <div className="flex flex-col w-[50%]">
                <Link href={`/${name}`}>{truncatedName}</Link>
                <Link href={`/category/${category}`} className='opacity-50 text-sm'>{truncatedCategory}</Link>
            </div>

            <div className="flex flex-col border-l-2 border-l-white pl-2">

                <div className='flex items-center gap-1'>
                    <div className={`w-2 h-2 rounded-full ${status === "Online" ? 'bg-main-green' : 'bg-main-gray'}`}/>
                    <span>{status}</span>
                </div>

                {
                    status === "Online" && (
                        <div className='flex items-center gap-1'>
                            <div className='w-2 h-2 bg-main-purple rounded-full'/>
                            <span>{viewers}</span>
                        </div>
                    )
                }
            </div>


        </div>
    );
};

export default FollowingCard;