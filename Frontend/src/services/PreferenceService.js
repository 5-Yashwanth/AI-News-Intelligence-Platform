import axios from "axios";

const API_URL = "http://localhost:8080/preferences";

const getToken = () => {
    return localStorage.getItem("token");
};

const PreferenceService = {

    getPreferences: async () => {
        const response = await axios.get(API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });

        return response.data;
    },

    updatePreferences: async (preferences) => {
        const response = await axios.put(API_URL, preferences, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });

        return response.data;
    }
};

export default PreferenceService;