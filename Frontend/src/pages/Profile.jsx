import Sidebar from "../components/Sidebar/Sidebar";
import "../styles/profile.css";

function Profile() {

    const email = localStorage.getItem("email");

    return (

        <>
            <Sidebar />

            <div style={{ marginLeft: "280px", padding: "40px" }}>

                <h1>My Profile</h1>

                <div className="profile-card">

                    <div className="avatar">

                        👤

                    </div>

                    <h2>{email}</h2>

                    <p>AI News Reader</p>

                </div>

            </div>

        </>

    );

}

export default Profile;