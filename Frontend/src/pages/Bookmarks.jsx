import { useEffect, useState } from "react";
import NewsCard from "../components/NewsCard/NewsCard";
import axios from "axios";

function Bookmarks() {

    const [bookmarks, setBookmarks] = useState([]);

    useEffect(() => {
        loadBookmarks();
    }, []);

    const loadBookmarks = async () => {

        try {

            const token = localStorage.getItem("token");

            const response = await axios.get(
                "http://localhost:8080/bookmarks",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setBookmarks(response.data);

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <div className="dashboard">

            <h1>My Bookmarks</h1>

            <div className="news-grid">

                {bookmarks.map(article => (

                    <NewsCard
                        key={article.id}
                        article={article}
                    />

                ))}

            </div>

        </div>

    );
}

export default Bookmarks;