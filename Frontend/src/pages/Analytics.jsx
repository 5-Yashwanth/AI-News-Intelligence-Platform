import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    PieChart,
    Pie,
    Cell,
    Legend
} from "recharts";
import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar/Sidebar";
import Navbar from "../components/Navbar/Navbar";
import AnalyticsService from "../services/AnalyticsService";
import "../styles/analytics.css";

const COLORS = [

    "#2563eb",
    "#10b981",
    "#f59e0b",
    "#ef4444",
    "#8b5cf6",
    "#06b6d4"

];

function Analytics() {

    const [analytics, setAnalytics] = useState(null);

    useEffect(() => {

        fetchAnalytics();

    }, []);

    const barData = [

        {
            name: "News",
            value: analytics?.totalArticles || 0
        },

        {
            name: "Bookmarks",
            value: analytics?.totalBookmarks || 0
        },

        {
            name: "History",
            value: analytics?.articlesRead || 0
        }

    ];

    const pieData = analytics?.categoryStats?.map(item => ({

        name: item.category,

        value: item.count

    })) || [];

    const fetchAnalytics = async () => {

        try {

            const response = await AnalyticsService.getAnalytics();

            setAnalytics(response.data);

        } catch (error) {

            console.error(error);

        }

    };

    if (!analytics) {

        return <h2>Loading Analytics...</h2>;

    }

    return (

        <>
            <Sidebar />

<div style={{ marginLeft: "280px", padding: "30px" }}>

    <Navbar />

    <h1>Analytics Dashboard</h1>

    <div className="analytics-grid">

        <div className="analytics-card">
            <h3>Total Articles</h3>
            <h1>{analytics.totalArticles}</h1>
        </div>

        <div className="analytics-card">
            <h3>Bookmarks</h3>
            <h1>{analytics.totalBookmarks}</h1>
        </div>

        <div className="analytics-card">
            <h3>Articles Read</h3>
            <h1>{analytics.articlesRead}</h1>
        </div>

        <div className="analytics-card">
            <h3>Favorite Category</h3>
            <h1>{analytics.favoriteCategory}</h1>
        </div>

    </div>

    {/* Charts MUST be inside this div */}

    <div className="charts">

        <div className="chart-card">

            <h2>Platform Statistics</h2>

            <ResponsiveContainer width="100%" height={300}>

                <BarChart
                    data={barData}
                    margin={{
                        top: 20,
                        right: 20,
                        left: 0,
                        bottom: 10
                    }}
                >
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />

                    <Bar
                        dataKey="value"
                        fill="#2563eb"
                        radius={[8, 8, 0, 0]}
                    />

                </BarChart>

            </ResponsiveContainer>

        </div>

        <div className="chart-card">

            <h2>Favorite Category</h2>

            <ResponsiveContainer width="100%" height={300}>

                <PieChart>

                    <Pie
                        data={pieData}
                        dataKey="value"
                        nameKey="name"
                        outerRadius={100}
                        label={({ name, percent }) =>
                            `${name} ${(percent * 100).toFixed(0)}%`
                        }
                    >
                        {pieData.map((entry, index) => (

                        <Cell
                            key={index}
                            fill={COLORS[index % COLORS.length]}
                        />

                        ))}
                    </Pie>

                    <Legend />

                </PieChart>

            </ResponsiveContainer>

        </div>

    </div>

</div>

        </>


    );

}

export default Analytics;