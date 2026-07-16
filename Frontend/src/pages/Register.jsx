import "../styles/register.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import api from "../services/api";

function Register() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleRegister = async () => {

        try {

            const response = await api.post("/auth/register", {
                name,
                email,
                password
            });

            alert(response.data);

        } catch (error) {

            alert("Registration Failed");
            console.log(error);

        }

    };

    return (
        <div className="register-container">

            <div className="register-box">

                <h1>Create Account</h1>

                <input
                    type="text"
                    placeholder="Full Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />

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

                <button onClick={handleRegister}>
                    Register
                </button>

                <p>
                    Already have an account?{" "}
                    <Link to="/">Login</Link>
                </p>

            </div>

        </div>
    );
}

export default Register;