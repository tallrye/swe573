package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.AnswerRequestDto;
import com.tallrye.wlearn.dto.QuestionRequestDto;
import com.tallrye.wlearn.service.QuestionService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class QuestionEntityControllerTest extends AbstractEntityControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private final QuestionController sut = new QuestionController(questionService);

    @Test
    public void createQuestion() {
        //Prepare
        final QuestionRequestDto request = QuestionRequestDto.builder().contentId(0L).text("someText").build();
        //Test
        sut.createQuestion(currentUser, request);
        //Verify
        verify(questionService, times(1)).createQuestionByContentId(currentUser, request);
    }

    @Test
    public void deleteQuestion() {
        //Test
        sut.deleteQuestion(currentUser, 0L);
        //Verify
        verify(questionService, times(1)).deleteQuestionById(0L, currentUser);
    }

    @Test
    public void getLearningPath() {
        //Test
        sut.getLearningPath(currentUser, 0L);
        //Verify
        verify(questionService, times(1)).getLearningSteps(currentUser, 0L);
    }

    @Test
    public void answer() {
        //Prepare
        final AnswerRequestDto request = AnswerRequestDto.builder().choiceId(0L).questionId(0L).build();
        //Test
        sut.answer(currentUser, request);
        //Verify
        verify(questionService, times(1)).giveAnswer(currentUser, request);
    }

}
