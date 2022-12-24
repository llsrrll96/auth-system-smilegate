import './App.css';
import LoginView from './components/loginBody/LoginView';
import Signup from "./components/SignupBody/Signup";
import { Route, Routes } from "react-router-dom";
import Home from './Home';


function App() {
  return (
    <div className="container">
      <Routes>
        <Route path="/signup" element={<Signup />} />
        <Route path="/login" element={<LoginView />} />
        <Route path="/" element={<Home />} />
      </Routes>
    </div>
  );
}

export default App;
