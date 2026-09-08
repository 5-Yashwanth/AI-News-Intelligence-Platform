import { useEffect, useState } from "react";
import PreferenceService from "../services/PreferenceService";
import "../styles/Preferences.css";
function Preferences() {

    const [preferences, setPreferences] = useState({
        aiMl: true,
        software: true,
        indiaNews: true,
        bigTech: true,
        worldBusiness: true,
        sportsMovies: true
    });

    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState("");

    useEffect(() => {
        loadPreferences();
    }, []);

    const loadPreferences = async () => {
        try {
            const data = await PreferenceService.getPreferences();
            setPreferences(data);
        } catch (error) {
            console.error("Failed to load preferences:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (event) => {
        const { name, checked } = event.target;

        setPreferences((previous) => ({
            ...previous,
            [name]: checked
        }));
    };

    const savePreferences = async () => {
        try {
            await PreferenceService.updatePreferences(preferences);
            setMessage("Preferences saved successfully!");

            setTimeout(() => {
                setMessage("");
            }, 3000);

        } catch (error) {
            console.error("Failed to save preferences:", error);
            setMessage("Failed to save preferences.");
        }
    };

    if (loading) {
        return <h2>Loading preferences...</h2>;
    }

    return (
        <div className="preferences-page">

            <h1>📰 News Preferences</h1>

            <p>
                Select the categories you want to see in your personalized news feed.
            </p>
            <p
                style={{
                    color: "#2563eb",
                    fontWeight: "600",
                    marginBottom: "20px"
                }}
>
                {
                    Object.values(preferences).filter(
                    (value) => value === true
                    ).length
                } of 6 categories selected
            </p>
            <div className="preferences-container">

                <label>
                    <input
                        type="checkbox"
                        name="aiMl"
                        checked={preferences.aiMl}
                        onChange={handleChange}
                    />
                    AI & ML
                </label>

                <label>
                    <input
                        type="checkbox"
                        name="software"
                        checked={preferences.software}
                        onChange={handleChange}
                    />
                    Software
                </label>

                <label>
                    <input
                        type="checkbox"
                        name="indiaNews"
                        checked={preferences.indiaNews}
                        onChange={handleChange}
                    />
                    India News
                </label>

                <label>
                    <input
                        type="checkbox"
                        name="bigTech"
                        checked={preferences.bigTech}
                        onChange={handleChange}
                    />
                    Big Tech & Startups
                </label>

                <label>
                    <input
                        type="checkbox"
                        name="worldBusiness"
                        checked={preferences.worldBusiness}
                        onChange={handleChange}
                    />
                    World & Business
                </label>

                <label>
                    <input
                        type="checkbox"
                        name="sportsMovies"
                        checked={preferences.sportsMovies}
                        onChange={handleChange}
                    />
                    Sports & Movies
                </label>

            </div>

            <button onClick={savePreferences}>
                Save Preferences
            </button>

            {message && <p>{message}</p>}

        </div>
    );
}

export default Preferences;