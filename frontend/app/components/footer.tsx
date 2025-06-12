import Image from "next/image";
import logo from "@/public/logo.png";

const Footer = () => {

    const authors: string[] = ["Miłosz Ludwinek", "Paweł Kupis", "Dawid Mróz"]

    return (
        <footer className='w-[calc(100%+1rem)] h-fit bg-main-footer flex flex-col items-center pb-4'>
            <div className='flex items-center gap-1 py-2'>
                <Image src={logo} alt="Pedro Logo" width={36} height={36}/>
                <span className='text-2xl opacity-80'>PEDRO</span>
            </div>
            {
                authors.map((author) => {
                    return (
                        <span key={author} className='opacity-80'>
                                {author}
                            </span>
                    )
                })
            }

        </footer>
    );
};

export default Footer;