import "./NewsCard.css";
import { FaRegBookmark } from "react-icons/fa";
import { useState } from "react";

import BookmarkService from "../../services/BookmarkService";

function NewsCard({ article }) {

    const [showSummary, setShowSummary] = useState(false);


    const saveBookmark = async () => {

        try {

            await BookmarkService.saveBookmark(article.id);

            alert("Bookmark Saved!");

        } catch (error) {

            console.error(error);

            alert("Failed to save bookmark");

        }

    };


    const result =
        article.sentiment?.toLowerCase() || "";


    const keywords =
        article.keywords
            ? article.keywords
                .split(",")
                .map(keyword => keyword.trim())
                .filter(keyword => keyword.length > 0)
            : [];


    return (

        <>

            {/* NEWS CARD */}

            <div className="news-card">

                {/* BOOKMARK */}

                <button
                    className="bookmark-btn"
                    onClick={saveBookmark}
                >
                    <FaRegBookmark />
                </button>


                {/* IMAGE */}

                <img
                    src={
                        article.imageUrl ||
                        "https://placehold.co/600x350/2563eb/ffffff?text=Technology"
                    }
                    alt={article.title}
                    className="news-image"
                />


                <div className="news-content">

                    {/* CATEGORY */}

                    <span className="category">
                        {article.category}
                    </span>


                    {/* SENTIMENT */}

                    <p
                        className={
                            result.includes("positive")
                                ? "sentiment positive"
                                : result.includes("negative")
                                ? "sentiment negative"
                                : "sentiment neutral"
                        }
                    >

                        {
                            result.includes("positive")
                                ? "😊 Positive"
                                : result.includes("negative")
                                ? "😟 Negative"
                                : "😐 Neutral"
                        }

                    </p>


                    {/* KEYWORDS */}

                    <div className="keywords">

                        {
                            keywords.map((word, index) => (

                                <span
                                    key={index}
                                    className="keyword"
                                >
                                    {word}
                                </span>

                            ))
                        }

                    </div>


                    {/* TITLE */}

                    <h2>
                        {article.title}
                    </h2>


                    {/* SOURCE */}

                    <p className="source">
                        {article.source}
                    </p>


                    {/* DATE */}

                    <p className="date">

                        {
                            new Date(
                                article.publishedDate
                            ).toLocaleDateString()
                        }

                    </p>


                    {/* ACTIONS */}

                    <div className="card-actions">

                        <button
                            className="summary-btn"
                            onClick={() => setShowSummary(true)}
                        >
                            🧠 AI Summary
                        </button>


                        <a
                            href={article.url}
                            target="_blank"
                            rel="noopener noreferrer"
                            className="read-more"
                        >
                            Read Article →
                        </a>

                    </div>

                </div>

            </div>


            {/* AI SUMMARY MODAL
                IMPORTANT:
                This is OUTSIDE the news-card.
            */}

            {showSummary && (

                <div
                    className="summary-modal"
                    onClick={() => setShowSummary(false)}
                >

                    <div
                        className="summary-box"
                        onClick={(event) => event.stopPropagation()}
                    >

                        <h2>
                            🧠 AI Summary
                        </h2>


                        <p>
                            {
                                article.summary ||
                                "AI summary unavailable."
                            }
                        </p>


                        <button
                            onClick={() => setShowSummary(false)}
                        >
                            Close
                        </button>

                    </div>

                </div>

            )}

        </>

    );

}

export default NewsCard;