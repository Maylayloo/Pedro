import Link from "next/link";
import FollowingCard from "@/app/components/followingCard";
import Footer from "@/app/components/footer";

const Sidebar = () => {

    interface following {
        name: string,
        pfp: string,
        status: "Online" | "Offline",
        category?: string,
        viewers?: number;
    }

    // for now
    const tmp_followingList: following[] = [
        {
            name: "Knight34",
            pfp: "",
            category: "League of Legends",
            status: "Online",
            viewers: 984
        },
        {
            name: "Potatooooo123",
            pfp: "",
            category: "Cooking",
            status: "Online",
            viewers: 729
        },
        {
            name: "DJ Softy",
            pfp: "",
            category: "Programming",
            status: "Online",
            viewers: 404
        },
        {
            name: "Moreo GCAAA",
            pfp: "",
            status: "Offline",
        },
        {
            name: "Gael 2137",
            pfp: "",
            status: "Offline",
        },

    ]
    // for now
    const loggedIn = true;

    return (
        <section className="sticky left-0 top-0 flex h-screen w-fit flex-col justify-between px-2 items-center
                 pt-24 text-white max-sm:hidden lg:w-[264px] bg-secondary-bg border-r-2 border-r-black">

            <span className='text-3xl mb-8'>OBSERWOWANI</span>

            {
                loggedIn ? (
                    <div className='flex flex-col flex-1 gap-6'>
                        {
                            tmp_followingList.map((following: following) => {
                                return (
                                    <FollowingCard
                                        key={following.name}
                                        name={following.name}
                                        status={following.status}
                                        pfp = {following.pfp}
                                        category = {following.category}
                                        viewers = {following.viewers}
                                    />
                                )
                            })
                        }
                        {/*does not work yet*/}
                        <button className='cursor-pointer text-main-purple'>
                            - - - - - - pokaż więcej - - - - - -
                        </button>
                    </div>


                ) : (
                    <div className='flex flex-col flex-1 gap-6 items-center'>
                        <Link href='/login' className='bg-main-gray auth-btn'>
                            Zaloguj się
                        </Link>
                        <Link href='/register' className='bg-main-purple auth-btn hidden md:block'>
                            Zarejestruj się
                        </Link>
                    </div>
                )
            }
            <Footer/>

        </section>
    );
};

export default Sidebar;