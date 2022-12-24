import React, { useState } from 'react';
import {useNavigate} from 'react-router-dom'
import Logo from '../Header/Logo';
import './Signup.css'
import RightLayoutLinks from "../Header/HeaderLinks/RightLayoutLinks";

const SignupView = props => {
  const options = [
    { value: "type", text: "직접 입력" },
    { value: "naver.com", text: "naver.com" },
    { value: "google.com", text: "google.com" },
    { value: "hanmail.net", text: "hanmail.net" },
    { value: "nate.com", text: "nate.com" },
    { value: "daum.net", text: "daum.net" },
  ];

  const [signupRequestBody, setSignupRequestBody] = useState({
    username: "",
    email: "",
    password: "",
  });

  const [passCheckText, setPassCheckText] = useState("비밀번호 일치하지 않음");

  const [domainSelected, setDomainSelected] = useState(options[0].value);

  const [domain, setDomain] = useState("");

  const [passCheck, setPassCheck] = useState("");

  const [isPassCheck, setIsPassCheck] = useState(false);

  const navigate = useNavigate();

  const onSignupInputChangeHandler = (event) => {
    setSignupRequestBody({
      ...signupRequestBody,
      [event.target.name]: event.target.value,
    });
    verifyEqualPasswordCheckAnd(event.target.value);
  };

  const onSelectDomainHandler = (event) => {
    // 옵션 도메인 선택시
    if (event.target.value !== "type") {
      setDomain(event.target.value);
    } else {
      setDomain("");
    }
    setDomainSelected(event.target.value);
  };

  const onInputDomainHandler = (event) => {
    if (domainSelected === "type") {
      setDomain(event.target.value);
    }
  };

  const onInputPassCheckChangeHandler = (event) => {
    verifyEqualPasswordAnd(event.target.value);
    setPassCheck(event.target.value);
  };

  const verifyEqualPasswordAnd = (passcheck) => {
    if (passcheck === signupRequestBody.password) {
      setPassCheckText("비밀번호 일치");
      setIsPassCheck(true);
    } else {
      setPassCheckText("비밀번호 일치하지 않음");
      setIsPassCheck(false);
    }
  };

  const verifyEqualPasswordCheckAnd = (password) => {
    if (password === passCheck) {
      setPassCheckText("비밀번호 일치");
      setIsPassCheck(true);
    } else {
      setPassCheckText("비밀번호 일치하지 않음");
      setIsPassCheck(false);
    }
  };

  // main page 이동
  const goToMain = () => {
    alert('회원가입 성공!')
    navigate('/login');
  };


  // 회원가입 과정
  const onSubmitHandler = (event) => {
    event.preventDefault();
    signupRequestBody.email = signupRequestBody.email + "@" + domain;
    console.log(JSON.stringify(signupRequestBody));

    fetch('/auth/api/v1/sign/signup', {
      method: "POST",
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(signupRequestBody),
    })
      .then((response) => {
        return response.json();
      })
      .then((result) => (
        result.userId ? goToMain() : alert("입력을 확인해 주세요.")
      ));

  };

  return (
    <div className="signup_main">
      <RightLayoutLinks />
      <Logo />
      <div className="signup_div">
        <div className="signup_text">
          <h3>회원가입</h3>
        </div>
        <div className="signup_form">
          <form onSubmit={onSubmitHandler}>
            <div>
              <p>
                <label htmlFor="username">이름</label>
              </p>
              <input
                onChange={onSignupInputChangeHandler}
                type="text"
                id="username"
                name="username"
                required
              />
            </div>

            <p>
              <label htmlFor="email">이메일</label>
            </p>
            <div className="email_div">
              <div className="email1">
                <input
                  onChange={onSignupInputChangeHandler}
                  type="text"
                  id="email"
                  name="email"
                  required
                />
                &nbsp;@ &nbsp;
              </div>

              <div className="email2">
                <input
                  type="text"
                  id="domain"
                  name="domain"
                  value={domain}
                  onChange={onInputDomainHandler}
                  required
                />
                <select
                  value={domainSelected}
                  className="box"
                  id="domain-list"
                  onChange={onSelectDomainHandler}
                >
                  {options.map((option) => (
                    <option key={option.value} value={option.value}>
                      {option.text}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <div>
              <p>
                <label htmlFor="password">비밀번호</label>
              </p>
              <input
                onChange={onSignupInputChangeHandler}
                type="password"
                name="password"
                id="password"
                required
              />

              <div className="pass_chk_div">
                <p>
                  <label htmlFor="password_chk">비밀번호 재입력</label>
                </p>
                <input
                  type="password"
                  id="password_chk"
                  onChange={onInputPassCheckChangeHandler}
                  required
                />
              </div>
              <div
                className={
                  isPassCheck
                    ? "password_check_text invalid"
                    : "password_check_text"
                }
              >
                <p>{passCheckText}</p>
              </div>
            </div>

            <input type="submit" id="btn" value="회원가입" />
          </form>
        </div>
      </div>
    </div>
  );
};

export default SignupView;