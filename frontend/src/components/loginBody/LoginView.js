import React, { useState } from 'react';
import { useNavigate, Link } from "react-router-dom";
import HeaderWrap from '../Header/HeaderWrap';
import './LoginView.css'

const LoginView = props => {

  const [requestBody, setRequestBody] = useState({
    email: "",
    password: "",
  })

  const navigate = useNavigate();

  const loginInputChangeHandler = event => {
    setRequestBody({
      ...requestBody,
      [event.target.name]: event.target.value,
    })
  }

  // main page 이동
  const goToMain = () => {
    alert('로그인 성공!')
    navigate(-1);
  };


  const onSubmitHandler = (event) => {
    event.preventDefault();

    console.log(JSON.stringify(requestBody));

    fetch('/auth/api/v1/sign/signin', {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestBody),
    })
      .then((response) => {
        return response.json();
      })
      .then((result) =>{
        console.log(result);
        
        result.accessToken ? goToMain() : alert("입력을 확인해 주세요.");
      });
  };

    return (
      <div className="login_main">
        <div className="header">
          <HeaderWrap />
        </div>
        <div className="login_text">
          <h3>로그인</h3>
        </div>
        <div className="form_container">
          <div className="form_div">
            <form onSubmit={onSubmitHandler}>
              <label>
                이메일
                <br />
                <input
                  type="text"
                  id="email"
                  name="email"
                  value={requestBody.email}
                  onChange={loginInputChangeHandler}
                />
              </label>
              <br />
              <label>
                비밀번호
                <br />
                <input
                  type="password"
                  id="password"
                  name="password"
                  value={requestBody.password}
                  onChange={loginInputChangeHandler}
                />
              </label>
              <br />
              <div className="signuptext_div">
                <Link to="/signup">회원가입</Link>
              </div>

              <input id="submit" type="submit" value="로그인" />
            </form>
          </div>
        </div>
      </div>
    );
};


export default LoginView;