import "./categorybar.css";

const categories = [
    "All",
    "Technology",
    "Business",
    "Sports",
    "Health",
    "Science",
    "Entertainment"
];

function CategoryBar({ selected, onSelect }) {
    return (
        <div className="category-bar">
            {categories.map((category) => (
                <button
                    key={category}
                    className={
                        selected === category
                            ? "category-btn active"
                            : "category-btn"
                    }
                    onClick={() => onSelect(category)}
                >
                    {category}
                </button>
            ))}
        </div>
    );
}

export default CategoryBar;