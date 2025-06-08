import type { Metadata } from "next";
import {Geist, Geist_Mono, Pixelify_Sans} from "next/font/google";
import "./globals.css";

const pixels = Pixelify_Sans({
  variable: "--font-pixelify-sans",
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
    <html lang="en">
      <body
        className={`${pixels.variable}`}
      >
        {children}
      </body>
    </html>
  );
}
