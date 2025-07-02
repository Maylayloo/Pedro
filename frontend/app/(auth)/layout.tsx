import React from 'react';

const Layout = ({children,}: Readonly<{
    children: React.ReactNode;
}>) => {
    return (
        <div className="h-screen w-full flex-center">
            <div className="p-8 bg-secondary-bg shadow-lg rounded-xl">
                {children}
            </div>
        </div>
    );
};

export default Layout;