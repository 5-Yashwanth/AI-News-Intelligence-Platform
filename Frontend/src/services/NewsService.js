import axios from "axios";

const API_URL = "http://localhost:8080";

const getNews = () => {
    return axios.get(`${API_URL}/news`);
};

const getNewsById = (id) => {

    const token = localStorage.getItem("token");

    return axios.get(

        `http://localhost:8080/news/${id}`,

        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }

    );

};

export default {
    getNews,
    getNewsById
};