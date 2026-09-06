import "./sidebar.css";

import DashboardIcon from "@mui/icons-material/Dashboard";
import ArticleIcon from "@mui/icons-material/Article";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import PsychologyIcon from "@mui/icons-material/Psychology";
import AnalyticsIcon from "@mui/icons-material/Analytics";
import SettingsIcon from "@mui/icons-material/Settings";
import LogoutIcon from "@mui/icons-material/Logout";
import HistoryIcon from "@mui/icons-material/History";
import { NavLink } from "react-router-dom";

function Sidebar() {
    return (
        <div className="sidebar">

            <div className="logo">
                🤖 AI News
            </div>

            <ul>

                <li>
                    <DashboardIcon />
                    <span>Dashboard</span>
                </li>

                <li>
                    <ArticleIcon />
                    <span>Latest News</span>
                </li>

                <NavLink
                    to="/bookmarks"
                    style={{ textDecoration: "none", color: "inherit" }}
                >
                    <li>
                        <BookmarkIcon />
                        <span>Bookmarks</span>
                    </li>
                </NavLink>

                <NavLink
                    to="/history"
                    style={{ textDecoration: "none", color: "inherit" }}
                >
                    <li>
                        <HistoryIcon />
                        <span>History</span>
                    </li>
                </NavLink>
                <li>
                    <TrendingUpIcon />
                    <span>Trending</span>
                </li>

                <li>
                    <PsychologyIcon />
                    <span>AI Summary</span>
                </li>

                <NavLink
                    to="/analytics"
                    style={{ textDecoration: "none", color: "inherit" }}
                >
                    <li>
                        <AnalyticsIcon />
                        <span>Analytics</span>
                    </li>
                </NavLink>

                <li>
                    <SettingsIcon />
                    <span>Settings</span>
                </li>

            </ul>

            <button className="logout-btn">
                <LogoutIcon />
                Logout
            </button>

        </div>
    );
}

export default Sidebar;