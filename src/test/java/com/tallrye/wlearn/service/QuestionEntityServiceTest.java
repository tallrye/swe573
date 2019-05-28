package com.tallrye.wlearn.service;

import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.dto.AnswerRequestDto;
import com.tallrye.wlearn.dto.QuestionRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.LearningStepsResponseDto;
import com.tallrye.wlearn.entity.*;
import com.tallrye.wlearn.exception.CreatedByException;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.repository.ContentRepository;
import com.tallrye.wlearn.repository.LearningPathRepository;
import com.tallrye.wlearn.repository.QuestionRepository;
import com.tallrye.wlearn.entity.ContentEntity;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class QuestionEntityServiceTest extends AbstractServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private LearningPathRepository learningPathRepository;

    @Mock
    private ConfigurableConversionService smepConversionService;


    @InjectMocks
    private final QuestionService sut = new QuestionService(questionRepository, contentRepository,
            smepConversionService, learningPathRepository);


    @Test(expected = ResourceNotFoundException.class)
    public void createQuestion_ResourceNotFound() {
        //Prepare
        final QuestionRequestDto questionRequestDto = TestUtils.createDummyQuestionRequest();
        when(contentRepository.findById(questionRequestDto.getContentId())).thenReturn(Optional.empty());
        //Test
        sut.createQuestionByContentId(currentUser, questionRequestDto);
    }

    @Test(expected = CreatedByException.class)
    public void createQuestion_CreateBy() {
        //Prepare
        final QuestionRequestDto questionRequestDto = TestUtils.createDummyQuestionRequest();
        final ContentEntity contentEntity = TestUtils.createDummyContent();
        contentEntity.setCreatedBy(1L);
        when(contentRepository.findById(questionRequestDto.getContentId())).thenReturn(Optional.of(contentEntity));
        //Test
        sut.createQuestionByContentId(currentUser, questionRequestDto);
    }

    @Test
    public void createQuestion_Success() {
        //Prepare
        final QuestionRequestDto questionRequestDto = TestUtils.createDummyQuestionRequest();
        final ContentEntity contentEntity = TestUtils.createDummyContent();
        final QuestionEntity questionEntity = TestUtils.createDummyQuestion();
        contentEntity.setCreatedBy(currentUser.getId());
        when(contentRepository.findById(questionRequestDto.getContentId())).thenReturn(Optional.of(contentEntity));
        when(smepConversionService.convert(questionRequestDto, QuestionEntity.class)).thenReturn(questionEntity);
        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.createQuestionByContentId(currentUser, questionRequestDto);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }


    @Test(expected = ResourceNotFoundException.class)
public void deleteQuestion__ResourceNotFound() {
        //Prepare
        when(questionRepository.findById(0L)).thenReturn(Optional.empty());
        //Test
        sut.deleteQuestionById(0L, currentUser);
    }

    @Test(expected = CreatedByException.class)
public void deleteQuestion__CreateBy() {
        //Prepare
        final QuestionEntity questionEntity = TestUtils.createDummyQuestion();
        questionEntity.setCreatedBy(1L);
        when(questionRepository.findById(0L)).thenReturn(Optional.of(questionEntity));
        //Test
        sut.deleteQuestionById(0L, currentUser);
    }

    @Test
public void deleteQuestion__Success() {
        //Prepare
        final QuestionEntity questionEntity = TestUtils.createDummyQuestion();
        questionEntity.setCreatedBy(currentUser.getId());
        when(questionRepository.findById(0L)).thenReturn(Optional.of(questionEntity));
        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.deleteQuestionById(0L, currentUser);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }


    @Test
    public void getLearningPath() {
        //Prepare
        final List<LearningPathEntity> learningPathEntityList = TestUtils.createDummyLearningStepList();
        final List<QuestionEntity> questionEntityList = TestUtils.createDummyQuetionList();
        final List<ChoiceEntity> choiceEntityList = TestUtils.createDummyChoiceList();
        final ContentEntity contentEntity = TestUtils.createDummyContent();
        final List<ContentEntity> contentEntityList = new ArrayList<>();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        contentEntityList.add(contentEntity);
        questionEntityList.get(0).setChoiceEntityList(choiceEntityList);
        contentEntity.setTopicEntity(topicEntity);
        contentEntity.setQuestionEntityList(questionEntityList);
        topicEntity.setContentEntityList(contentEntityList);
        when(contentRepository.findById(0L)).thenReturn(Optional.of(contentEntity));
        when(learningPathRepository.findByUserIdAndContentId(currentUser.getId(), contentEntity.getId()))
                .thenReturn(learningPathEntityList);
        //Test
        final ResponseEntity<LearningStepsResponseDto> responseEntity = sut.getLearningSteps(currentUser, 0L);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void answer_Success() {
        //Prepare
        final AnswerRequestDto answerRequestDto = TestUtils.createDummyAnswerRequest();
        final QuestionEntity questionEntity = TestUtils.createDummyQuestion();
        final LearningPathEntity learningPathEntity = TestUtils.createDummyLearningStep();
        when(questionRepository.findById(answerRequestDto.getQuestionId())).thenReturn(Optional.of(questionEntity));
        when(learningPathRepository
                .findByUserIdAndContentIdAndQuestionIdAndAnswerId(currentUser.getId(), questionEntity.getContentEntity().getId(),
                        answerRequestDto.getQuestionId(), answerRequestDto.getChoiceId()))
                .thenReturn(Optional.of(learningPathEntity));
        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.giveAnswer(currentUser, answerRequestDto);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);

    }


}
