package com.tallrye.wlearn.service;

import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.dto.EnrollmentRequestDto;
import com.tallrye.wlearn.dto.PublishRequestDto;
import com.tallrye.wlearn.dto.TopicRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.TopicResponseDto;
import com.tallrye.wlearn.entity.ContentEntity;
import com.tallrye.wlearn.entity.TopicEntity;
import com.tallrye.wlearn.exception.CreatedByException;
import com.tallrye.wlearn.exception.NotValidTopicException;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.repository.TopicRepository;
import com.tallrye.wlearn.repository.UserRepository;
import com.tallrye.wlearn.repository.WikiDataRepository;
import com.tallrye.wlearn.entity.QuestionEntity;
import com.tallrye.wlearn.entity.UserEntity;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TopicEntityServiceTest extends AbstractServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WikiDataRepository wikiDataRepository;

    @Mock
    private ConfigurableConversionService smepConversionService;

    @InjectMocks
    private final TopicService sut = new TopicService(topicRepository, userRepository, wikiDataRepository,
            smepConversionService);

    @Test
    public void getAllTopics() {
        //Prepare
        final List<TopicEntity> topicEntityList = TestUtils.createDummyTopicList();
        when(topicRepository.findByPublished(true)).thenReturn(topicEntityList);
        when(smepConversionService.convert(topicEntityList.get(0), TopicResponseDto.class))
                .thenReturn(TestUtils.createDummyTopicResponse());
        //Test
        final ResponseEntity<List<TopicResponseDto>> responseEntity = sut.getAllTopics(currentUser);
        //Verify
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getTopicsCreatedBy_UserNotFound() {
        //Prepare
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        //Test
        sut.getTopicsCreatedBy("username", currentUser);
    }

    @Test
    public void getTopicsCreatedBy_Success() {
        //Prepare
        final UserEntity userEntity = TestUtils.createDummyUser();
        final List<TopicEntity> topicEntityList = TestUtils.createDummyTopicList();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        when(topicRepository.findByCreatedBy(userEntity.getId())).thenReturn(topicEntityList);
        when(smepConversionService.convert(topicEntityList.get(0), TopicResponseDto.class))
                .thenReturn(TestUtils.createDummyTopicResponse());
        //Test
        final ResponseEntity<List<TopicResponseDto>> responseEntity = sut.getTopicsCreatedBy("username", currentUser);
        //Verify
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getTopic_ResourceNotFound() {
        //Prepare
        when(topicRepository.findById(0L)).thenReturn(Optional.empty());
        //Test
        sut.getTopicById(0L, currentUser);
    }

    @Test
    public void getTopic_Success() {
        //Prepare
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        when(topicRepository.findById(0L)).thenReturn(Optional.of(topicEntity));
        when(smepConversionService.convert(topicEntity, TopicResponseDto.class))
                .thenReturn(TestUtils.createDummyTopicResponse());
        //Test
        final ResponseEntity<TopicResponseDto> responseEntity = sut.getTopicById(0L, currentUser);
        //Verify
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void createTopic_Success() {
        //Prepare
        final TopicRequestDto topicRequestDto = TestUtils.createDummyTopicRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        final TopicResponseDto topicResponseDto = TestUtils.createDummyTopicResponse();
        when(topicRepository.findById(topicRequestDto.getId())).thenReturn(Optional.of(topicEntity));
        when(smepConversionService.convert(topicRequestDto, TopicEntity.class)).thenReturn(topicEntity);
        when(topicRepository.save(topicEntity)).thenReturn(topicEntity);
        when(smepConversionService.convert(topicEntity, TopicResponseDto.class)).thenReturn(topicResponseDto);
        //Test
        final ResponseEntity<TopicResponseDto> responseEntity = sut.createTopic(currentUser, topicRequestDto);
        //Verify
        assertNotNull(responseEntity.getBody());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void publishStatusUpdate_TopicNotFound() {
        //Prepare
        final PublishRequestDto publishRequestDto = TestUtils.createDummyPublishRequest();
        when(topicRepository.findById(publishRequestDto.getTopicId())).thenReturn(Optional.empty());
        //Test
        sut.publishStatusUpdate(currentUser, publishRequestDto);
    }

    @Test(expected = CreatedByException.class)
    public void changeStatus_CreateBy() {
        //Prepare
        final PublishRequestDto publishRequestDto = TestUtils.createDummyPublishRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        topicEntity.setCreatedBy(1L);
        when(topicRepository.findById(publishRequestDto.getTopicId())).thenReturn(Optional.of(topicEntity));
        //Test
        sut.publishStatusUpdate(currentUser, publishRequestDto);
    }

    @Test(expected = NotValidTopicException.class)
    public void changeStatus_NotValidTopic_NullOrEmptyContentList() {
        //Prepare
        final PublishRequestDto publishRequestDto = TestUtils.createDummyPublishRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        topicEntity.setContentEntityList(null);
        topicEntity.setCreatedBy(currentUser.getId());
        when(topicRepository.findById(publishRequestDto.getTopicId())).thenReturn(Optional.of(topicEntity));
        //Test
        sut.publishStatusUpdate(currentUser, publishRequestDto);
    }

    @Test(expected = NotValidTopicException.class)
    public void changeStatus_NotValidTopic_NullOrEmptyQuestionList() {
        //Prepare
        final PublishRequestDto publishRequestDto = TestUtils.createDummyPublishRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        final List<ContentEntity> contentEntityList = TestUtils.createDummyContentList();
        contentEntityList.get(0).setQuestionEntityList(null);
        topicEntity.setContentEntityList(contentEntityList);
        topicEntity.setCreatedBy(currentUser.getId());
        when(topicRepository.findById(publishRequestDto.getTopicId())).thenReturn(Optional.of(topicEntity));
        //Test
        sut.publishStatusUpdate(currentUser, publishRequestDto);
    }

    @Test
    public void changeStatus_Success() {
        //Prepare
        final PublishRequestDto publishRequestDto = TestUtils.createDummyPublishRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        final List<ContentEntity> contentEntityList = TestUtils.createDummyContentList();
        final List<QuestionEntity> questionEntityList = TestUtils.createDummyQuetionList();
        contentEntityList.get(0).setQuestionEntityList(questionEntityList);
        topicEntity.setContentEntityList(contentEntityList);
        topicEntity.setCreatedBy(currentUser.getId());
        when(topicRepository.findById(publishRequestDto.getTopicId())).thenReturn(Optional.of(topicEntity));
        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.publishStatusUpdate(currentUser, publishRequestDto);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void deleteTopic_TopicNotFound() {
        //Prepare
        when(topicRepository.findById(0L)).thenReturn(Optional.empty());
        //Test
        sut.deleteTopicById(0L, currentUser);
    }

    @Test(expected = CreatedByException.class)
    public void deleteTopic_CreateBy() {
        //Prepare
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        topicEntity.setCreatedBy(1L);
        when(topicRepository.findById(0L)).thenReturn(Optional.of(topicEntity));
        //Test
        sut.deleteTopicById(0L, currentUser);
    }

    @Test
    public void deleteTopic_Success() {
        //Prepare
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        topicEntity.setCreatedBy(currentUser.getId());
        when(topicRepository.findById(topicEntity.getId())).thenReturn(Optional.of(topicEntity));
        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.deleteTopicById(0L, currentUser);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void enroll_TopicResourceNotFound() {
        //Prepare
        final EnrollmentRequestDto enrollmentRequestDto = TestUtils.createDummyEnrollmentRequest();
        when(topicRepository.findById(enrollmentRequestDto.getTopicId())).thenReturn(Optional.empty());
        //Test
        sut.enrollToTopicByUsername(currentUser, enrollmentRequestDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void enroll_ResourceNotFound() {
        //Prepare
        final EnrollmentRequestDto enrollmentRequestDto = TestUtils.createDummyEnrollmentRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        when(topicRepository.findById(enrollmentRequestDto.getTopicId())).thenReturn(Optional.of(topicEntity));
        when(userRepository.findByUsername(enrollmentRequestDto.getUsername())).thenReturn(Optional.empty());
        //Test
        sut.enrollToTopicByUsername(currentUser, enrollmentRequestDto);
    }

    @Test
    public void enroll_Success() {
        //Prepare
        final EnrollmentRequestDto enrollmentRequestDto = TestUtils.createDummyEnrollmentRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        final UserEntity userEntity = TestUtils.createDummyUser();
        when(topicRepository.findById(enrollmentRequestDto.getTopicId())).thenReturn(Optional.of(topicEntity));
        when(userRepository.findByUsername(enrollmentRequestDto.getUsername())).thenReturn(Optional.of(userEntity));
        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.enrollToTopicByUsername(currentUser, enrollmentRequestDto);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void getTopicsByUser_ResourceNotFound() {
        //Prepare
        when(userRepository.findById(0L)).thenReturn(Optional.empty());
        //Test
        sut.getTopicsByEnrolledUserId(currentUser, 0L);
    }

    @Test
    public void getTopicsByUser() {
        //Prepare
        final UserEntity userEntity = TestUtils.createDummyUser();
        final List<TopicEntity> enrolledTopicEntities = TestUtils.createDummyTopicList();
        final TopicResponseDto topicResponseDto = TestUtils.createDummyTopicResponse();
        when(userRepository.findById(0L)).thenReturn(Optional.of(userEntity));
        when(topicRepository.findTopicByEnrolledUsersContainsAndPublished(userEntity, true)).thenReturn(enrolledTopicEntities);
        when(smepConversionService.convert(enrolledTopicEntities.get(0), TopicResponseDto.class)).thenReturn(topicResponseDto);
        //Test
        final ResponseEntity<List<TopicResponseDto>> responseEntity = sut.getTopicsByEnrolledUserId(currentUser, 0L);
        //Verify
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
    }


}
