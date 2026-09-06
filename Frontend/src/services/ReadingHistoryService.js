import axios from "axios";

const API_URL = "http://localhost:8080/history";

const saveHistory = (newsId) => {

    return axios.post(

        `${API_URL}/${newsId}`,

        {},

        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
        }

    );

};

const getHistory = () => {

    return axios.get(API_URL, {

        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },

    });

};

export default {

    saveHistory,

    getHistory,

};