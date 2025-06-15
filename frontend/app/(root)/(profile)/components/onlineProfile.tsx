interface props {
    username: string;
}

const OnlineProfile = ({username, }: props) => {
    return (
        <div>
            {username} is online
        </div>
    );
};

export default OnlineProfile;