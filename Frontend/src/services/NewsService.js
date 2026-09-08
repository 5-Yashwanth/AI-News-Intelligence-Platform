import axios from "axios";

const API_URL = "http://localhost:8080";


// ==========================================
// Get All News
// ==========================================

const getNews = () => {

    return axios.get(`${API_URL}/news`);

};


// ==========================================
// Get Recommended News
// ==========================================

const getRecommendedNews = () => {

    const token = localStorage.getItem("token");

    return axios.get(
        `${API_URL}/recommendations`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

};


// ==========================================
// Get News By ID
// ==========================================

const getNewsById = (id) => {

    const token = localStorage.getItem("token");

    return axios.get(
        `${API_URL}/news/${id}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

};


export default {

    getNews,

    getRecommendedNews,

    getNewsById

};