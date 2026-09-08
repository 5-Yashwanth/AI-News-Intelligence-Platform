import "./categorybar.css";

const categories = [
    "All",
    "AI & ML",
    "Software",
    "India News",
    "Big Tech & Startups",
    "World & Business",
    "Sports & Movies"
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