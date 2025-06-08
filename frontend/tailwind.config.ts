import type { Config } from "tailwindcss";

export default {
    content: [
        "./pages/**/*.{js,ts,jsx,tsx,mdx}",
        "./components/**/*.{js,ts,jsx,tsx,mdx}",
        "./app/**/*.{js,ts,jsx,tsx,mdx}",
    ],
    theme: {
        extend: {
            screens: {

            },
            fontFamily: {
                pixels: ['var(--font-pixelify-sans)']
            },
            colors: {

            },
        },
    },
    plugins: [],
} satisfies Config;
