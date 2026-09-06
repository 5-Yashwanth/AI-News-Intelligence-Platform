import axios from "axios";

const API_URL = "http://localhost:8080/bookmarks";

const saveBookmark = (newsId) => {

    const token = localStorage.getItem("token");

    return axios.post(
        `${API_URL}/${newsId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};

export default {
    saveBookmark
};