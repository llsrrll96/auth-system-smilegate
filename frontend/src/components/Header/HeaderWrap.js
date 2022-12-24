import React from 'react';
import CoreBanner from './CoreHeader/CoreBanner';
import RightLayoutLinks from './HeaderLinks/RightLayoutLinks';

import styles from "./Header.module.css";

const HeaderWrap = props => {
    return (

        <div className={styles.container}>
            <div className="header_wrap">
            <div className="lnb_container">
                <RightLayoutLinks />
            </div>
            <div className="core_header">
                <CoreBanner />
            </div>
            </div>
        </div>
    );
};



export default HeaderWrap;