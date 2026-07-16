import axios from "axios";

const API_URL = "http://localhost:8080/auth";

const register = (user) => {
    return axios.post(`${API_URL}/register`, user);
};

const login = (credentials) => {
    return axios.post(`${API_URL}/login`, credentials);
};

export default {
    register,
    login,
};