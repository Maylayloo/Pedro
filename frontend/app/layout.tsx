import type { Metadata } from "next";
import {Pixelify_Sans} from "next/font/google";
import "./globals.css";

const pixels = Pixelify_Sans({
  variable: "--font-pixelify",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Pedro",
  description: "Pedro Pedro Pedro Pedro",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en"
          className={`${pixels.className}`}
    >
      <body className='bg-main-bg text-white'>
        {children}
      </body>
    </html>
  );
}
