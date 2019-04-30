import React, { Component } from 'react';
import image_1 from '../img/improve1.jpg'
import image_2 from '../img/improve2.jpg'
import { Link } from "react-router-dom";

class Home extends Component {

    render() {
        return (
            <React.Fragment>
                <div className="mt-5 mb-5 text-left sectionPadding">
                    <div className="container">
                        <div className="row align-items-center">
                            <div className="col-md-6">
                                <h4 className="mt-5 mb-5">This is <strong className="strong">your space</strong></h4>
                                <p>
                                    W-Learn is a free-to-use knowledge sharing platform. Find your path and start learning.
                                <br /><br />
                                    <Link className="btn btn-outline-primary btn-xl" to="/glossary">Get Started</Link>

                                </p>
                            </div>
                            <div className="col-md-1"></div>
                            <div className="col-md-5">
                                <img src={image_1} className="img-fluid" alt="" />
                            </div>
                        </div>
                    </div>
                </div>
                <div className="mt-5 text-left bg-alt sectionPadding">
                    <div className="container">
                        <div className="row align-items-center">
                            <div className="col-md-5">
                                <img src={image_2} className="img-fluid" alt="" />
                            </div>
                            <div className="col-md-1"></div>
                            <div className="col-md-6">
                                <h4 className="mt-5 mb-5">Polish & share your <strong className="strong">knowledge</strong></h4>
                                <p>
                                    Learn from others, explore new world. And vice versa; tell what's on your mind, help others to polish their knowledge.
                                    <br /><br />
                                    <Link className="btn btn-outline-primary btn-xl" to="/glossary">Get Started</Link>
                                </p>
                            </div>
                        </div>
                    </div>

                </div>

            </React.Fragment>
        )
    }
}

export default Home;