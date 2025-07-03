import FollowButton from "@/app/(root)/(profile)/components/followButton";

interface props {
    username: string,
    userId: number,
}
const OfflineProfile = ({username, userId,}: props) => {
    return (
        <div className="flex flex-col items-center justify-center gap-4 mt-12">
            <span className='text-2xl'>Niestety <span className='text-main-green'>{username}</span> nie prowadzi teraz transmisji. Wpadnij później</span>
            <video autoPlay loop muted controls={false} width={256} className={'grayscale rounded-2xl'}>
                <source src='/hobbes2594_pedro.mp4'/>
            </video>
            <FollowButton username={username} userId={userId}/>
        </div>
    );
};

export default OfflineProfile;