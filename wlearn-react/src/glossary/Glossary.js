import React, { Component } from 'react';
import { Button, Row, InputGroup } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHashtag } from '@fortawesome/free-solid-svg-icons'
import { API_BASE_URL, TOPIC_LIST_SIZE } from '../constants';
import './Glossary.css';
import axios from 'axios';

class Glossary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false,
            input: ''
        };
        this.loadTopicList = this.loadTopicList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadTopicList(page, size = TOPIC_LIST_SIZE) {
        let url = API_BASE_URL + "/topics?page=" + page + "&size=" + size;
        axios.get(url).then(res => {
            this.setState({
                topics: res.data.content,
                page: res.data.page,
                size: res.data.size,
                totalElements: res.data.totalElements,
                totalPages: res.data.totalPages,
                last: res.data.last,
                isLoading: false
            })
        }).catch(err => {
            this.setState({ isLoading: false })
        });
    }

    onChangeHandler(e) {
        this.setState({
            input: e.target.value,
        })
    }

    componentDidMount() {
        this.loadTopicList(this.state.page, this.state.size);
    }


    handleLoadMore() {
        console.log("more topics loaded!..");
        this.loadTopicList(this.state.page + 1);
    }


    render() {
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }
        const topics = this.state.topics;
        const topicsView = topics.filter(topic => this.state.input === '' || topic.title.toLowerCase().indexOf(this.state.input) > -1).map((topic, topicIndex) => {
            return (
                <Row className="mb-1" key={topicIndex}>
                    <div className="card mb-4" style={{ minWidth: "100%" }}>
                        <div className="row no-gutters ">
                            <div className="col-md-4">
                                <div className="clear p-4">
                                    <img src={"https://via.placeholder.com/300x200"} className="img-fluid fullWidth" alt={topic.title} />
                                    <br />

                                    <br />
                                    <Button variant="primary" block>Details</Button>
                                </div>
                            </div>
                            <div className="col-md-8">
                                <div className="card-body text-left">
                                    <h5 className="card-title text-info text-justify mb-1">{topic.title} </h5>
                                    <small className="text-left"><strong>by</strong> @{topic.createdBy.username} {' '}</small>
                                    <hr />
                                    <p className="card-text text-justify">{topic.description}</p>
                                    <p className="card-text text-justify"><FontAwesomeIcon icon={faHashtag} /> wiki:
                                        {topic.wikiData.map((wiki, idx) => {
                                        return <a key={idx} href={wiki} target="_blank" rel="noopener noreferrer" className="ml-2 badge badge-pill badge-primary">{wiki.substring(wiki.indexOf("Q"), wiki.length)}</a>
                                    })}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </Row>
            )
        })

        return (
            <React.Fragment>
                <div className="pageHeader text-left">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12">
                                Explore
                            </div>
                        </div>
                    </div>
                </div>
                <div className="container">
                    <div className="row  mt-5 mb-5">
                        <div className="col-md-12">
                            <InputGroup>
                                <input value={this.state.input} placeholder="Search topics" className="form-control searchInput" type="text" onChange={this.onChangeHandler.bind(this)} />
                            </InputGroup>
                        </div>
                    </div>
                    <div className="col-md-12">
                        {topicsView}
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default withRouter(Glossary);