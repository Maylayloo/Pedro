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

                <section className='flex min-h-screen flex-1 flex-col pb-6 pt-18'>
                    <div className='w-full'>
                        {children}
                    </div>
                </section>
            </div>

        </main>
    );
}
