import React, { Component } from 'react';

export default class WikiLabel extends Component {
    render() {
        return (
            <div className="col-md-3">
                <h4 className="serif" style={{ fontSize: '22px' }}>Things to Consider</h4>
                <hr />
                <p style={{ fontSize: '14px', textAlign: 'justify' }}>
                    Lorem ipsum dolor sit, amet consectetur adipisicing elit. Neque ipsam ut consectetur vel excepturi alias laboriosam totam
                    fuga reprehenderit officiis, sed aliquam accusamus repellat laborum! Fuga cupiditate porro exercitationem quod.
                </p>
                <br />
                Example Image: https://picsum.photos/id/498/1920/800
            </div>
        )

    }
}

