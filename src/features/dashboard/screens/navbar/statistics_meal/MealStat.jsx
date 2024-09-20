import React from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';

// Register chart.js components
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const MealStat = () => {
    // Dummy data for energy consumed each day for a family of 2 members
    const daysInMonth = 30;
    const energyConsumedData = Array.from({ length: daysInMonth }, () => Math.floor(Math.random() * 1000) + 1500);

    // Chart data and configuration
    const data = {
        labels: Array.from({ length: daysInMonth }, (_, i) => `Day ${i + 1}`),
        datasets: [
            {
                label: 'Energy Consumed (Kcal)',
                data: energyConsumedData,
                borderColor: 'rgba(75, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                fill: true,
                tension: 0.4, // To make the line smooth
            },
        ],
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Daily Energy Consumption for Family of 2 Members (Kcal)',
            },
        },
    };

    // Style for background and container
    const containerStyle = {
        width: '80%',
        margin: '0 auto',
        backgroundColor: '#ffffff', // Set background to white
        padding: '20px',
        borderRadius: '10px',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    };

    return (
        <div style={containerStyle}>
            <h2>Meal Statistics</h2>
            <Line data={data} options={options} />
        </div>
    );
};

export default MealStat;
