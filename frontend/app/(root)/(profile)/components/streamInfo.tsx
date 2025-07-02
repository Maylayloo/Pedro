interface props {
    username?: string,
    title?: string,
    category?: string,
    // userId: number,
}

const StreamInfo = ({title, category, username}: props) => {
    return (
        <section className='flex'>
            <div className='bg-red-200 flex-1'>
                TU ZDJECIE
            </div>
            <div className='bg-green-200 flex-3'>
                TU OPIS
            </div>
            <div className='bg-blue-200 flex-1'>
                TU PRZYCISKI I WIDZOWIE
            </div>
        </section>
    );
};

export default StreamInfo;