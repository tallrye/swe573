import React, { Component } from 'react';
import { ACCESS_TOKEN, API_BASE_URL } from "../constants";
import axios from "axios";
import { Col, ListGroup, Row, Tab } from "react-bootstrap";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHashtag, faArrowAltCircleRight, faChevronRight, faPlus } from '@fortawesome/free-solid-svg-icons'

class Topic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topic: {
                contentList: []
            },
            activeTab: '',
            isLoading: false
        };
        this.loadTopicById = this.loadTopicById.bind(this);
    }

    loadTopicById() {
        let url = API_BASE_URL + `/topics/topic/${this.props.match.params.topicId}`;

        const options = {
            method: 'GET',
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) },
            url
        };

        axios(options)
            .then(res => {
                if (res.data.contentList.length > 0) {
                    this.setState({ topic: res.data, activeTab: res.data.contentList[0].id })
                } else {
                    this.setState({ topic: res.data })
                }

            }).catch(err => {
                this.setState({ isLoading: false })
            });
    }

    componentDidMount() {
        this.loadTopicById();
    }


    render() {
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }

        const topic = this.state.topic;
        const contentList = this.state.topic.contentList;

        const contentListLeftColumn = contentList.map((content, contentId) => {
            return (
                <ListGroup.Item key={contentId} action eventKey={content.id}>
                    {contentId + 1} - {content.title} <FontAwesomeIcon icon={faChevronRight} />
                </ListGroup.Item>
            )
        });

        const contentListRightColumn = contentList.map((content, contentId) => {
            return (
                <Tab.Pane key={contentId} eventKey={content.id}>
                    <div className="text-left" dangerouslySetInnerHTML={{ __html: content.text }} ></div>
                </Tab.Pane>
            )
        });

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
                <div className="bg-alt sectionPadding text-left">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12">
                                <h4 className="mb-4">Explore <strong>{topic.title}</strong></h4>
                                <p>
                                    {topic.description}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

                {
                    this.state.activeTab && (
                        <React.Fragment>
                            <div className="container mt-5">
                                <div className="row col-md-12 text-left">
                                    <h4>Learning <strong>Path</strong> <Link className="btn btn-success btn-sm ml-2" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-7px' }} to={`/topic/${topic.id}/content`}><FontAwesomeIcon icon={faPlus} /> Add Material</Link></h4>
                                </div>
                            </div>
                            <Tab.Container id="list-group-tabs-example" defaultActiveKey={this.state.activeTab}>
                                <div className="container mt-5 text-left" >
                                    <Row>
                                        <Col sm={3}>
                                            <ListGroup>
                                                {contentListLeftColumn}
                                            </ListGroup>
                                        </Col>
                                        <Col sm={9}>
                                            <Tab.Content>
                                                {contentListRightColumn}
                                            </Tab.Content>
                                        </Col>
                                    </Row>
                                </div>
                            </Tab.Container>
                        </React.Fragment>
                    )
                }


            </React.Fragment>
        )
    }
}

export default Topic;