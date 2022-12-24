import React from 'react';
import "./RightLayoutLinks.css";
import { Link } from "react-router-dom";

const RightLayoutLinks = props => {
    return (
      <div className="usermenu">
        <ul>
          <li>
            <Link to="/login">로그인</Link>
          </li>

          <li>
            <Link to="/signup">회원가입</Link>
          </li>
        </ul>
      </div>
    );
};


export default RightLayoutLinks;