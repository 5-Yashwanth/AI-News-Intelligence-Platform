import { useEffect, useState } from "react";
import NewsService from "../services/NewsService";

function Dashboard() {

    const [articles, setArticles] = useState([]);
    const [loading, setLoading] = useState(true);

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

    return (
        <div>
            <h1>AI News Dashboard</h1>

            {articles.map((article, index) => (
                <div key={index}>
                    <h2>{article.title}</h2>
                    <p>{article.description}</p>
                    <hr />
                </div>
            ))}
        </div>
    );
}

export default Dashboard;