import React from 'react';
import HeaderWrap from './components/Header/HeaderWrap';

const Home = props => {
  const show = () => {
      console.log("reloading");
    }
    return (
      <div>
        <div id="header_wrap">
          <HeaderWrap />
        </div>
        <div className="main_banner">{show()}</div>
      </div>
    );
};


export default Home;