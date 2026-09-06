import "./breakingnews.css";

function BreakingNews({ article }) {

    if (!article) return null;

    return (
        <div className="breaking-news">

            <div className="overlay">

                <span className="breaking-tag">
                    🔥 Breaking News
                </span>

                <h1>{article.title}</h1>

                <p>
                    {article.source}
                </p>

                <a
                    href={article.url}
                    target="_blank"
                    rel="noreferrer"
                >
                    Read Full Story →
                </a>

            </div>

        </div>
    );
}

export default BreakingNews;