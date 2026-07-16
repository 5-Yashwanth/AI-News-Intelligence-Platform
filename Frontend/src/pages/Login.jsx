import "../styles/login.css";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import AuthService from "../services/AuthService";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleLogin = async () => {
        try {

            const response = await AuthService.login({
                email,
                password
            });

            localStorage.setItem("token", response.data.token);

            alert("Login Successful");

            navigate("/dashboard");

        } catch (error) {

            alert("Invalid Email or Password");

            console.error(error);

        }
    };

    return (
        <div className="login-container">

            <div className="login-box">

                <h1>AI News</h1>

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button onClick={handleLogin}>
                    Login
                </button>

                <p>
                    Don't have an account?{" "}
                    <Link to="/register">Register</Link>
                </p>

            </div>

        </div>
    );
}

export default Login;