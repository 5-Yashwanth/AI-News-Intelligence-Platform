import "./navbar.css";

import SearchIcon from "@mui/icons-material/Search";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { NavLink } from "react-router-dom";

function Navbar({ searchTerm, setSearchTerm }) {
    return (
        <div className="navbar">

            <div className="search-box">

                <SearchIcon />

                <input
                    type="text"
                    placeholder="Search news..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />

            </div>

            <div className="navbar-right">

                <NotificationsNoneIcon className="nav-icon" />

                <NavLink
                    to="/profile"
                    style={{ color: "inherit", textDecoration: "none" }}
                >
                    <AccountCircleIcon className="profile-icon" />
                </NavLink>

            </div>

        </div>
    );
}

export default Navbar;