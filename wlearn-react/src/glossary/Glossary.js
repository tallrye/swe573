import React, { Component } from 'react';
import { Row, InputGroup } from 'react-bootstrap';
import { Link, withRouter } from 'react-router-dom';
import PageHeader from "../components/PageHeader";
import { WikiLabels } from "../components/Wiki";
import axios from 'axios';
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';

class Glossary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            input: '',
            loading: true
        };
        this.loadTopicList = this.loadTopicList.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }

    loadTopicList() {
        let url = resolveEndpoint('getAllTopics', []);

        axios.get(url).then(res => {
            this.setState({
                topics: res.data,
                loading: false
            })
        }).catch(err => {
            console.log(err)
        });
    }

    handleSearch(e) {
        this.setState({
            input: e.target.value,
        })
    }

    componentDidMount() {
        this.loadTopicList();
    }

    render() {
        const { topics, input, loading } = this.state;

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="Explore" />
                        <div className="container">
                            <div className="row  mt-5 mb-5">
                                <div className="col-md-12">
                                    <InputGroup>
                                        <input value={input} placeholder="Search topics" className="form-control searchInput" type="text" onChange={this.handleSearch} />
                                    </InputGroup>
                                </div>
                            </div>
                            <div className="col-md-12">
                                {topics.filter(topic => input === '' || topic.title.toLowerCase().indexOf(input) > -1).map((topic, topicIndex) => {
                                    return (
                                        <Row className="mb-1" key={topicIndex}>
                                            <div className="card mb-4" style={{ minWidth: "100%" }}>
                                                <div className="row no-gutters ">
                                                    <div className="col-md-4">
                                                        <div className="clear p-4">
                                                            <img src={topic.imageUrl} className="img-fluid fullWidth mb-4" alt={topic.title} />
                                                            <Link className="btn btn-sm btn-primary fullWidth" to={`/topic/preview/${topic.id}`}>Details</Link>
                                                        </div>
                                                    </div>
                                                    <div className="col-md-8">
                                                        <div className="card-body text-left">
                                                            <h5 className="card-title text-info text-justify mb-1">{topic.title} </h5>
                                                            <small className="text-left"><strong>by </strong> @{topic.createdBy} {' '}</small>
                                                            <hr />
                                                            <p className="card-text text-justify">{topic.description}</p>
                                                            <WikiLabels
                                                                wikis={topic.wikiData}
                                                            />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </Row>
                                    )
                                })}
                            </div>
                        </div>
                    </React.Fragment>
                )}
            </React.Fragment>
        )
    }
}

export default withRouter(Glossary);