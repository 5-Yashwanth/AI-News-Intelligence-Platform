import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar/Sidebar";
import Navbar from "../components/Navbar/Navbar";
import ReadingHistoryService from "../services/ReadingHistoryService";
import NewsCard from "../components/NewsCard/NewsCard";

function History() {

    const [history, setHistory] = useState([]);

    useEffect(() => {
        fetchHistory();
    }, []);

    const fetchHistory = async () => {

        try {

            const response = await ReadingHistoryService.getHistory();

            setHistory(response.data);

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <>
            <Sidebar />

            <div style={{ marginLeft: "280px", padding: "30px" }}>

                <Navbar />

                <h1>Reading History</h1>

                <div className="news-grid">

                    {history.map(item => (

                        <NewsCard
                            key={item.id}
                            article={item.news}
                        />

                    ))}

                </div>

            </div>
        </>

    );

}

export default History;