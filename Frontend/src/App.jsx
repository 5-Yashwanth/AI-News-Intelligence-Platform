import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Bookmarks from "./pages/Bookmarks";
import ArticleDetails from "./pages/ArticleDetails";
import Profile from "./pages/Profile";
import History from "./pages/History";
import Analytics from "./pages/Analytics";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/bookmarks" element={<Bookmarks />} />
                <Route path="/news/:id" element={<ArticleDetails />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/history" element={<History />} />
                <Route path="/analytics" element={<Analytics />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;