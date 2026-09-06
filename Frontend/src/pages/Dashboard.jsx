import { useEffect, useState } from "react";
import NewsService from "../services/NewsService";
import Sidebar from "../components/Sidebar/Sidebar";
import Navbar from "../components/Navbar/Navbar";
import NewsCard from "../components/NewsCard/NewsCard";
import "../styles/dashboard.css";
import BreakingNews from "../components/BreakingNews/BreakingNews";
import CategoryBar from "../components/CategoryBar/CategoryBar";


function Dashboard() {

    const [articles, setArticles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedCategory, setSelectedCategory] = useState("All");
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        fetchNews();
    }, []);

    const fetchNews = async () => {
        try {
            const response = await NewsService.getNews();
            console.log(response.data);

            setArticles(response.data);        
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <h2>Loading News...</h2>;
    }

    const filteredArticles = articles
        .filter(article =>
            selectedCategory === "All"
                ? true
                : article.category?.toLowerCase() ===
                selectedCategory.toLowerCase()
            )
        .filter(article =>
            article.title
                ?.toLowerCase()
                .includes(searchTerm.toLowerCase())
    );

    return (
        <>
        <Sidebar />

            <div style={{ marginLeft: "280px", padding: "30px" }}>

                <Navbar
                    searchTerm={searchTerm}
                    setSearchTerm={setSearchTerm}
                />
                <div className="dashboard-header">

                    <h1>Latest AI News</h1>

                    <p>
                        Stay updated with technology, AI, startups and world news.
                    </p>

                </div>

                <CategoryBar
                    selected={selectedCategory}
                    onSelect={setSelectedCategory}
                />

                <BreakingNews article={filteredArticles[0]} />

                <div className="news-grid">

                    {filteredArticles.map(article => (

                        <NewsCard
                        key={article.id}
                        article={article}
                        />

                    ))}

                </div>

            </div>
        </>
    );
}

export default Dashboard;