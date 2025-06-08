import Nav from "@/app/components/nav";
import Sidebar from "@/app/components/sidebar";

export default function HomeLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <main className='relative'>
            <Nav/>

            <div className="flex">
                <Sidebar/>

                <section className='flex min-h-screen flex-1 flex-col px-6 pb-6 pt-24
                         max-md:pb-14 sm:px-14 lg:px-10'>
                    <div className='w-full'>
                        {children}
                    </div>
                </section>
            </div>

        </main>
    );
}
