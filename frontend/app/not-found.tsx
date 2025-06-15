import Link from 'next/link'


export default function NotFound() {
    return (
        <div className='h-screen flex-center flex-col gap-4'>
            <span className='text-4xl'>Not Found</span>
            <video autoPlay loop muted controls={false} width={128} className={'grayscale'}>
                <source src='/hobbes2594_pedro.mp4'/>
            </video>
            <p className='text-2xl'>Could not find requested resource</p>
            <Link className='border border-white rounded-lg px-4 py-2 pointer' href="/">Return Home</Link>

            <span className='absolute bottom-1 left-1 opacity-30'>
                GIF credentials: <span className='px-2'>Hobbes2594</span>
            </span>
        </div>
    )
}