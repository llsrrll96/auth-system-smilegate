import React from 'react';
import './CoreBanner.css';
import CoreCategory from './CoreCategory';
import Logo from '../Logo';

const CoreBanner = props => { 
    return (
        <div className="header_container">
            <Logo />
            <CoreCategory />
        </div>
    );
};


export default CoreBanner;