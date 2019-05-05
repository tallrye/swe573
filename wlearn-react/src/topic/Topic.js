import React, { Component } from 'react';
import { ACCESS_TOKEN, API_BASE_URL } from "../constants";
import axios from "axios";
import { Col, ListGroup, Row, Tab, Button, Modal } from "react-bootstrap";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { createQuestion, createOption } from "../util/APIUtils";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronRight, faPlus, faTrash, faEdit } from '@fortawesome/free-solid-svg-icons'
import toast from "toasted-notes";

class Topic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topic: {
                contentList: []
            },
            activeTab: '',
            isLoading: false,
            show: false,
            showOptionModal: false,
            modalQuestionId: false,
            modalContentId: false

        };
        this.loadTopicById = this.loadTopicById.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleShowOption = this.handleShowOption.bind(this);
        this.handleCloseOption = this.handleCloseOption.bind(this);
        this.handleDeleteQuestionById = this.handleDeleteQuestionById.bind(this)
        this.handleDeleteContentById = this.handleDeleteContentById.bind(this)
    }

    handleClose() {
        this.setState({ show: false, modalContentId: false });
    }

    handleShow(contentId) {
        this.setState({ show: true, modalContentId: contentId });
    }

    handleCloseOption() {
        this.setState({ showOptionModal: false, modalQuestionId: false });
    }

    handleShowOption(questionId) {
        this.setState({ showOptionModal: true, modalQuestionId: questionId });
    }

    handleDeleteQuestionById(questionIdToDelete) {
        let url = API_BASE_URL + `/questions/${questionIdToDelete}`;

        const options = {
            method: 'DELETE',
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) },
            url
        };

        axios(options)
            .then(res => {
                toast.notify("Question deleted successfully.", { position: "top-right" });
                this.loadTopicById()
            }).catch(err => {
                console.log(err)
            });

    }

    handleDeleteContentById(contentIdToDelete) {
        let url = API_BASE_URL + `/contents/${contentIdToDelete}`;

        const options = {
            method: 'DELETE',
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) },
            url
        };

        axios(options)
            .then(res => {
                toast.notify("Material deleted successfully.", { position: "top-right" });
                this.loadTopicById()
            }).catch(err => {
                console.log(err)
            });

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
            const questions = content.questionList
            return (
                <Tab.Pane key={contentId} eventKey={content.id}>
                    <div className=" bg-alt materialBody">
                        <h4 className="mb-4 fontMedium">{content.title}
                            <Button className="btn-sm ml-2" variant="success" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} onClick={() => this.handleShow(content.id)}>
                                <FontAwesomeIcon icon={faPlus} /> Question
                            </Button>
                            <Button className="ml-2 btn-sm" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} variant="outline-danger" onClick={() => this.handleDeleteContentById(content.id)}><FontAwesomeIcon icon={faTrash} /></Button>
                            <Link className="btn  btn-outline-primary btn-sm ml-2" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} to={`/content/${content.id}`}><FontAwesomeIcon icon={faEdit} /></Link>
                        </h4>

                        <div className="text-left" dangerouslySetInnerHTML={{ __html: content.text }} ></div>
                        {
                            questions.length > 0 && (
                                <React.Fragment>
                                    <hr />
                                    {
                                        questions.map((question, questionId) => {
                                            const choices = question.choiceList
                                            return (
                                                <div key={questionId}>
                                                    <p><strong>Q{questionId + 1}:</strong> {question.text}
                                                        <Button className="btn-sm ml-2" variant="success" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} onClick={() => this.handleShowOption(question.id)}>
                                                            <FontAwesomeIcon icon={faPlus} /> Option
                                                    </Button>
                                                        <Button className="ml-2 btn-sm" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} variant="outline-danger" onClick={() => this.handleDeleteQuestionById(question.id)}><FontAwesomeIcon icon={faTrash} /></Button>
                                                    </p>

                                                    {
                                                        choices.length > 0 && (
                                                            <React.Fragment>
                                                                {
                                                                    choices.map((choice, choiceId) => {
                                                                        return (
                                                                            <div key={choiceId} style={{ paddingLeft: '20px' }}>
                                                                                <input type="radio" value="1" name="choice" /> {choice.text}
                                                                                {choice.correct && " (correct)"}
                                                                            </div>
                                                                        )
                                                                    })
                                                                }
                                                            </React.Fragment>
                                                        )
                                                    }
                                                    <hr />
                                                </div>
                                            )
                                        })
                                    }
                                </React.Fragment>
                            )
                        }

                    </div>
                </Tab.Pane>
            )
        });

        return (
            <React.Fragment>
                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>New Question</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Formik
                            initialValues={{ text: '' }}
                            validate={values => {
                                let errors = {};

                                if (!values.text) {
                                    errors.text = 'Question text is required';
                                }

                                return errors;
                            }}
                            onSubmit={(values, { setSubmitting }) => {
                                setTimeout(() => {

                                    let contentId = this.state.modalContentId;
                                    const newQuestion = {
                                        text: values.text
                                    };


                                    createQuestion(newQuestion, contentId)
                                        .then(res => {
                                            toast.notify("Question created successfully.", { position: "top-right" });
                                            this.handleClose()
                                            this.loadTopicById()
                                        }).catch(err => {
                                            toast.notify("Something went wrong!", { position: "top-right" });
                                        });

                                    setSubmitting(false);
                                }, 400);
                            }}
                        >
                            {({ isSubmitting }) => (
                                <Form>
                                    <div className="form-group row text-left">
                                        <label htmlFor="contentTitle" className="col-sm-12 col-form-label">Your <strong>Question</strong></label>
                                        <div className="col-sm-12">
                                            <Field type="text" name="text" id="text" className="form-control" />
                                            <ErrorMessage name="text" component="div" />
                                        </div>
                                    </div>

                                    <Button variant="success" type="submit" block disabled={isSubmitting}>Save</Button>
                                </Form>
                            )}
                        </Formik>
                    </Modal.Body>
                </Modal>
                <Modal show={this.state.showOptionModal} onHide={this.handleCloseOption}>
                    <Modal.Header closeButton>
                        <Modal.Title>New Option</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Formik
                            initialValues={{ text: '', correct: false }}
                            validate={values => {
                                let errors = {};

                                if (!values.text) {
                                    errors.text = 'Option text is required';
                                }

                                return errors;
                            }}
                            onSubmit={(values, { setSubmitting }) => {
                                setTimeout(() => {

                                    let questionId = this.state.modalQuestionId;
                                    const newOption = {
                                        text: values.text,
                                        correct: values.correct
                                    };


                                    createOption(newOption, questionId)
                                        .then(res => {
                                            toast.notify("Option created successfully.", { position: "top-right" });
                                            this.handleCloseOption()
                                            this.loadTopicById()
                                        }).catch(err => {
                                            toast.notify("Something went wrong!", { position: "top-right" });
                                        });

                                    setSubmitting(false);
                                }, 400);
                            }}
                        >
                            {({ isSubmitting }) => (
                                <Form>
                                    <div className="form-group row text-left">
                                        <label htmlFor="contentTitle" className="col-sm-12 col-form-label">Your <strong>Option</strong></label>
                                        <div className="col-sm-12">
                                            <Field type="text" name="text" id="text" className="form-control" />
                                            <ErrorMessage name="text" component="div" />
                                        </div>
                                    </div>
                                    <div className="form-group row text-left">
                                        <div className="col-sm-12">
                                            <Field type="checkbox" name="correct" id="correct" /> Is this Correct?
                                    </div>
                                    </div>

                                    <Button variant="success" type="submit" block disabled={isSubmitting}>Save</Button>
                                </Form>
                            )}
                        </Formik>
                    </Modal.Body>
                </Modal>
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
                            <div className="col-md-8">
                                <h4 className="mb-4">Explore <strong>{topic.title}</strong></h4>
                                <p>
                                    {topic.description}
                                </p>
                            </div>
                            <div className="col-md-4">
                                <img src={topic.imageUrl} className="img-fluid" alt="" />
                            </div>
                        </div>
                    </div>
                </div>

                <React.Fragment>
                    <div className="container mt-5">
                        <div className="row col-md-12 text-left">
                            <h4>
                                Learning <strong>Path</strong>
                                <Link className="btn btn-success btn-sm ml-2" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-7px' }} to={`/topic/${topic.id}/content`}><FontAwesomeIcon icon={faPlus} /> Material</Link>
                            </h4>
                        </div>
                    </div>
                    {
                        this.state.activeTab && (
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
                        )
                    }
                </React.Fragment>

            </React.Fragment>
        )
    }
}

export default Topic;