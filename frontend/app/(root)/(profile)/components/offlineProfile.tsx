interface props {
    username: string;
}
const OfflineProfile = ({username, }: props) => {
    return (
        <div>
            {username} is offline
        </div>
    );
};

export default OfflineProfile;