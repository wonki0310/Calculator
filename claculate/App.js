import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Home from "./components/Home";
import Login from "./components/Login";
import MyPage from "./components/MyPage";
import Posts from "./components/Posts";
import Search from "./components/Search";
import Recommend from "./components/Recommend";

export default function App() {
  return (
    <BrowserRouter>
      <nav style={{ display: "flex", gap: 12, padding: 12 }}>
        <Link to="/">Home</Link>
        <Link to="/login">Login</Link>
        <Link to="/mypage">MyPage</Link>
        <Link to="/posts">Posts</Link>
        <Link to="/search">Search</Link>
        <Link to="/recommend">Recommend</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/posts" element={<Posts />} />
        <Route path="/search" element={<Search />} />
        <Route path="/recommend" element={<Recommend />} />
      </Routes>
    </BrowserRouter>
  );
}
