import "../styles/register.css";
import { Link } from "react-router-dom";

function Register() {
    return (
        <div className="register-container">

            <div className="register-box">

                <h1>Create Account</h1>

                <input
                    type="text"
                    placeholder="Full Name"
                />

                <input
                    type="email"
                    placeholder="Email"
                />

                <input
                    type="password"
                    placeholder="Password"
                />

                <button>
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