import React, { Component } from 'react';
import { REQUEST_HEADERS } from "../constants";
import axios from "axios";
import { Link, withRouter } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import { Question } from "../components/LearningPath";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';

class ContentQuiz extends Component {
    constructor(props) {
        super(props);
        this.state = {
            questions: false,
            loading: true
        };
        this.loadContentById = this.loadContentById.bind(this);
    }


    loadContentById() {
        let url = resolveEndpoint('getQuestionsByContentId', [{ "slug1": this.props.match.params.contentId }]);

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({ questions: res.data, loading: false })
            }).catch(err => {
                console.log(err)
            });
    }

    componentDidMount() {
        this.loadContentById();
    }


    render() {

        const { questions, loading } = this.state;
        const { editable } = this.props

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="Content Quiz">
                            <Link to="" className="breadcrumbLink">
                                <span>!!TOPIC NAME!!</span>
                            </Link>
                        </PageHeader>

                        {
                            questions && (
                                <div className="bg-alt sectionPadding text-left">
                                    <div className="container">
                                        <div className="row">
                                            <div className="col-md-8">
                                                <h4 className="mb-4">Quiz: <strong>!! CONTENT NAME !!</strong>

                                                </h4>
                                            </div>
                                            <div className="col-md-12">
                                                {questions.length > 0 && (
                                                    <React.Fragment>
                                                        <hr />
                                                        {
                                                            questions.map((question, idx) => {
                                                                return (
                                                                    <Question
                                                                        key={idx}
                                                                        order={idx + 1}
                                                                        question={question}
                                                                        editable={editable}
                                                                        answered={false}
                                                                    />
                                                                )
                                                            })
                                                        }
                                                    </React.Fragment>
                                                )}

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )
                        }
                    </React.Fragment>
                )}
            </React.Fragment>
        )
    }
}

export default withRouter(ContentQuiz);