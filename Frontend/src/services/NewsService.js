import axios from "axios";

const API_URL = "http://localhost:8080";

const getNews = () => {
    return axios.get(`${API_URL}/news`);
};

export default {
    getNews,
};