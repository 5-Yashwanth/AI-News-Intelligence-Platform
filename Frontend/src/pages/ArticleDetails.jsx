import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import NewsService from "../services/NewsService";
import ReadingHistoryService from "../services/ReadingHistoryService";

import "../styles/article.css";

function ArticleDetails() {

    const { id } = useParams();

    const [article, setArticle] = useState(null);

    useEffect(() => {

        loadArticle();
        saveReadingHistory();

    }, [id]);


    const loadArticle = async () => {

        try {

            const response =
                await NewsService.getNewsById(id);

            setArticle(response.data);

        } catch (error) {

            console.error(error);

        }

    };


    const saveReadingHistory = async () => {

        try {

            await ReadingHistoryService.saveHistory(id);

        } catch (error) {

            console.error(error);

        }

    };


    if (!article) {

        return <h2>Loading...</h2>;

    }


    const sentimentResult =
        article.sentiment?.toLowerCase() || "";


    const keywords =
        article.keywords
            ? article.keywords
                .split(",")
                .map(keyword => keyword.trim())
                .filter(keyword => keyword.length > 0)
            : [];


    return (

        <div className="article-page">

            <img
                src={
                    article.imageUrl ||
                    "https://placehold.co/1200x600/2563eb/ffffff?text=News"
                }
                alt={article.title}
                className="article-image"
            />


            <div className="article-content">

                <span className="article-category">

                    {article.category}

                </span>


                <h1>

                    {article.title}

                </h1>


                {/* ARTICLE META */}

                <div className="article-meta">

                    <div
                        className={
                            sentimentResult.includes("positive")
                                ? "sentiment positive"
                                : sentimentResult.includes("negative")
                                ? "sentiment negative"
                                : "sentiment neutral"
                        }
                    >

                        {
                            sentimentResult.includes("positive")
                                ? "😊 Positive"
                                : sentimentResult.includes("negative")
                                ? "😟 Negative"
                                : "😐 Neutral"
                        }

                    </div>


                    <span>

                        {article.source}

                    </span>


                    <span>

                        {
                            new Date(
                                article.publishedDate
                            ).toLocaleDateString()
                        }

                    </span>

                </div>


                {/* DESCRIPTION */}

                {article.description && (

                    <div className="article-description">

                        <h3>

                            📰 Description

                        </h3>

                        <p>

                            {article.description}

                        </p>

                    </div>

                )}


                {/* AI SUMMARY */}

                <div className="summary-box">

                    <h3>

                        🤖 AI Summary

                    </h3>

                    <p>

                        {
                            article.summary ||
                            "AI summary unavailable."
                        }

                    </p>

                </div>


                {/* AI KEYWORDS */}

                <div className="article-keywords">

                    <h3>

                        🏷️ AI Keywords

                    </h3>


                    <div className="keyword-list">

                        {

                            keywords.length > 0

                                ? keywords.map(
                                    (keyword, index) => (

                                        <span
                                            key={index}
                                            className="keyword"
                                        >

                                            {keyword}

                                        </span>

                                    )
                                )

                                : (

                                    <p>
                                        No keywords available.
                                    </p>

                                )

                        }

                    </div>

                </div>


                {/* ORIGINAL ARTICLE */}

                <a
                    href={article.url}
                    target="_blank"
                    rel="noreferrer"
                    className="read-btn"
                >

                    Read Original Article →

                </a>

            </div>

        </div>

    );

}

export default ArticleDetails;