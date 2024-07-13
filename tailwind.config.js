/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        bgColor: '#fbfef7',
      },
      backgroundImage:{
        'bgImage': "url(/bg.png)",
        'dashImage': "url(/bg-dashboard.jpeg)",
      },


    },
  },
  plugins: [],
}