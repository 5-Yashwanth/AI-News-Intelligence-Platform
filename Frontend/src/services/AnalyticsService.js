import axios from "axios";

const API_URL = "http://localhost:8080/analytics";

const getAnalytics = () => {

    return axios.get(API_URL);

};

export default {

    getAnalytics,

};