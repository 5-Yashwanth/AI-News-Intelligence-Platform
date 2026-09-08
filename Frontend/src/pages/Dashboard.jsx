import { useEffect, useState } from "react";

import NewsService from "../services/NewsService";
import PreferenceService from "../services/PreferenceService";

import Sidebar from "../components/Sidebar/Sidebar";
import Navbar from "../components/Navbar/Navbar";
import NewsCard from "../components/NewsCard/NewsCard";
import BreakingNews from "../components/BreakingNews/BreakingNews";
import CategoryBar from "../components/CategoryBar/CategoryBar";

import "../styles/dashboard.css";


function Dashboard() {

    // ==========================================
    // News
    // ==========================================

    const [articles, setArticles] = useState([]);

    const [loading, setLoading] = useState(true);


    // ==========================================
    // Category Filter
    // ==========================================

    const [selectedCategory, setSelectedCategory] = useState("All");


    // ==========================================
    // Search
    // ==========================================

    const [searchTerm, setSearchTerm] = useState("");


    // ==========================================
    // User Preferences
    // ==========================================

    const [preferences, setPreferences] = useState({
        aiMl: true,
        software: true,
        indiaNews: true,
        bigTech: true,
        worldBusiness: true,
        sportsMovies: true
    });


    // ==========================================
    // Load News + Preferences
    // ==========================================

    useEffect(() => {

        fetchNews();
        fetchPreferences();

    }, []);


    // ==========================================
    // Fetch News
    // ==========================================

    const fetchNews = async () => {

        try {

            const response = await NewsService.getNews();

            console.log("News:", response.data);

            setArticles(response.data);

        } catch (error) {

            console.error(
                "Failed to fetch news:",
                error
            );

        } finally {

            setLoading(false);

        }
    };


    // ==========================================
    // Fetch Preferences
    // ==========================================

    const fetchPreferences = async () => {

        try {

            const data =
                await PreferenceService.getPreferences();

            console.log(
                "User Preferences:",
                data
            );

            setPreferences(data);

        } catch (error) {

            console.error(
                "Failed to fetch preferences:",
                error
            );

            // If preferences cannot be loaded,
            // keep all categories enabled.

            setPreferences({
                aiMl: true,
                software: true,
                indiaNews: true,
                bigTech: true,
                worldBusiness: true,
                sportsMovies: true
            });
        }
    };


    // ==========================================
    // Check Whether Category Is Enabled
    // ==========================================

    const isCategoryEnabled = (category) => {

        if (!category) {
            return false;
        }


        const value = category
            .trim()
            .toLowerCase();


        // ==========================================
        // New AI Categories
        // ==========================================

        if (value === "ai & ml") {
            return preferences.aiMl;
        }


        if (value === "software") {
            return preferences.software;
        }


        if (value === "india news") {
            return preferences.indiaNews;
        }


        if (value === "big tech & startups") {
            return preferences.bigTech;
        }


        if (value === "world & business") {
            return preferences.worldBusiness;
        }


        if (value === "sports & movies") {
            return preferences.sportsMovies;
        }


        // ==========================================
        // Old Categories
        // ==========================================
        // Existing database articles may still use
        // the old category names.
        //
        // Keep them visible for now.
        // ==========================================

        if (
            value === "technology" ||
            value === "business" ||
            value === "sports" ||
            value === "health" ||
            value === "science" ||
            value === "entertainment"
        ) {

            return true;

        }


        // ==========================================
        // Unknown Categories
        // ==========================================

        return true;
    };


    // ==========================================
    // Loading
    // ==========================================

    if (loading) {

        return <h2>Loading News...</h2>;

    }


    // ==========================================
    // Filter Articles
    // ==========================================

    const filteredArticles = articles

        // ======================================
        // Preference Filter
        // ======================================

        .filter(article =>
            isCategoryEnabled(article.category)
        )


        // ======================================
        // Category Bar Filter
        // ======================================

        .filter(article => {

            if (selectedCategory === "All") {
                return true;
            }


            return (
                article.category
                    ?.trim()
                    .toLowerCase() ===
                selectedCategory
                    .trim()
                    .toLowerCase()
            );

        })


        // ======================================
        // Search Filter
        // ======================================

        .filter(article =>

            article.title
                ?.toLowerCase()
                .includes(
                    searchTerm.toLowerCase()
                )

        );


    // ==========================================
    // UI
    // ==========================================

    return (

        <>

            <Sidebar />


            <div
                style={{
                    marginLeft: "280px",
                    padding: "30px"
                }}
            >

                {/* ==================================
                    Navbar
                ================================== */}

                <Navbar
                    searchTerm={searchTerm}
                    setSearchTerm={setSearchTerm}
                />


                {/* ==================================
                    Dashboard Header
                ================================== */}

                <div className="dashboard-header">

                    <h1>
                        Latest AI News
                    </h1>

                    <p>
                        Stay updated with technology,
                        AI, startups and world news.
                    </p>

                    <p
                        style={{
                            marginTop: "12px",
                            fontWeight: "600",
                            color: "#2563eb"
                        }}
                    >
                    ✨ Your Personalized Feed
                    </p>

                </div>


                {/* ==================================
                    Category Bar
                ================================== */}

                <CategoryBar
                    selected={selectedCategory}
                    onSelect={setSelectedCategory}
                />


                {/* ==================================
                    Breaking News
                ================================== */}

                <BreakingNews
                    article={filteredArticles[0]}
                />


                {/* ==================================
                    News Grid
                ================================== */}

                <div className="news-grid">

                    {filteredArticles.map(
                        (article) => (

                            <NewsCard
                                key={article.id}
                                article={article}
                            />

                        )
                    )}

                </div>


                {/* ==================================
                    No News Message
                ================================== */}

                {filteredArticles.length === 0 && (

                    <div
                        style={{
                            textAlign: "center",
                            padding: "50px",
                            color: "#6b7280"
                        }}
                    >

                        <h2>
                            No news available
                        </h2>

                        <p>
                            Try enabling more categories
                            in your preferences.
                        </p>

                    </div>

                )}

            </div>

        </>

    );
}


export default Dashboard;