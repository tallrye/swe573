import React, { Component } from 'react';
import { ACCESS_TOKEN, API_BASE_URL, TOPIC_LIST_SIZE } from "../constants";
import axios from "axios";
import { Badge, Button, Table } from "react-bootstrap";
import { Link } from "react-router-dom";

class UserCreatedTopicList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadUserCreatedTopics = this.loadUserCreatedTopics.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.handleDeleteTopicById = this.handleDeleteTopicById.bind(this);
    }

    loadUserCreatedTopics(page, size = TOPIC_LIST_SIZE) {
        const username = this.props.currentUser.username;
        let url = API_BASE_URL + `/users/${username}/topics/?page=${page}&size=${size}`;

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

    handleLoadMore() {
        console.log("more topics loaded!..");
        this.loadTopicList(this.state.page + 1);
    }

    handleDeleteTopicById(topicIdToDelete) {
        let url = API_BASE_URL + `/topics/topic/${topicIdToDelete}`;

        const options = {
            method: 'DELETE',
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) },
            url
        };

        axios(options)
            .then(res => {
                this.loadUserCreatedTopics(this.state.page, this.state.size)
            }).catch(err => {
                console.log(err)
            });

    }

    componentDidMount() {
        this.loadUserCreatedTopics(this.state.page, this.state.size)
    }

    render() {
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }
        const topics = this.state.topics;


        return (
            <React.Fragment>
                <div className="pageHeader text-left">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12">
                                My topics
                    </div>
                        </div>
                    </div>
                </div>
                <div className="container">
                    {
                        topics.map((topic, topicIndex) => {
                            return (
                                <div className="row mt-5">
                                    <div className="col-md-4" key={topicIndex}>
                                        <div className="card" style={{ padding: '20px' }}>
                                            <div className="card-bod">
                                                <h4>{topic.title}</h4>
                                                <p>{topic.description}</p>
                                                <p>{topic.wikiData.map((wiki, idx) => {
                                                    return <a key={idx} href={wiki} target="_blank" rel="noopener noreferrer" className="badge badge-pill badge-primary">{wiki.substring(wiki.indexOf("Q"), wiki.length)}</a>
                                                })}</p>
                                                <hr />
                                                <Link className="btn btn-sm btn-outline-primary" to={`/topic/${topic.id}`}>Details</Link>
                                                <Button className="ml-2 btn-sm" variant="outline-danger" onClick={() => this.handleDeleteTopicById(topic.id)}>Delete</Button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )
                        })
                    }

                </div>
            </React.Fragment>

        )
    }
}

export default UserCreatedTopicList;