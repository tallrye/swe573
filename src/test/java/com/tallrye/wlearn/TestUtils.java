package com.tallrye.wlearn;

import com.tallrye.wlearn.dto.*;
import com.tallrye.wlearn.entity.*;
import com.tallrye.wlearn.security.UserPrincipal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtils {

    public static ChoiceRequestDto createDummyChoiceRequest() {
        final ChoiceRequestDto choiceRequestDto = new ChoiceRequestDto();
        choiceRequestDto.setCorrect(true);
        choiceRequestDto.setQuestionId(0L);
        choiceRequestDto.setText("someText");
        return choiceRequestDto;
    }

    public static ChoiceEntity createDummyChoice() {
        final ChoiceEntity choiceEntity = new ChoiceEntity();
        choiceEntity.setCorrect(true);
        choiceEntity.setId(0L);
        choiceEntity.setText("someText");
        return choiceEntity;
    }

    public static List<ChoiceEntity> createDummyChoiceList() {
        final List<ChoiceEntity> choiceEntityList = new ArrayList<>();
        choiceEntityList.add(createDummyChoice());
        return choiceEntityList;
    }

    public static ContentRequestDto createDummyContentRequest() {
        final ContentRequestDto contentRequestDto = new ContentRequestDto();
        contentRequestDto.setId(0L);
        contentRequestDto.setTitle("someTitle");
        contentRequestDto.setTopicId(0L);
        contentRequestDto.setText("someText");
        return contentRequestDto;
    }

    public static ContentEntity createDummyContent() {
        final ContentEntity contentEntity = new ContentEntity();
        contentEntity.setId(0L);
        contentEntity.setTitle("someTitle");
        contentEntity.setText("someText");
        contentEntity.setTopicEntity(createDummyTopic());
        return contentEntity;
    }

    public static ContentResponseDto createDummyContentResponse() {
        final ContentResponseDto contentResponseDto = new ContentResponseDto();
        contentResponseDto.setId(0L);
        contentResponseDto.setTitle("someTitle");
        contentResponseDto.setText("someText");
        contentResponseDto.setTopicId(0L);
        return contentResponseDto;
    }

    public static TopicEntity createDummyTopic() {
        final TopicEntity topicEntity = new TopicEntity();
        topicEntity.setId(0L);
        topicEntity.setDescription("someDescription");
        topicEntity.setTitle("someTitle");
        topicEntity.setImageUrl("someImgUrl");
        topicEntity.setWikiDataEntitySet(createDummyWikiDataSet());
        topicEntity.setEnrolledUserEntities(createDummyUserSet());
        return topicEntity;
    }

    public static List<TopicEntity> createDummyTopicList() {
        final List<TopicEntity> topicEntityList = new ArrayList<>();
        topicEntityList.add(createDummyTopic());
        return topicEntityList;
    }

    public static QuestionEntity createDummyQuestion() {
        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(0L);
        questionEntity.setText("someText");
        questionEntity.setContentEntity(createDummyContent());
        return questionEntity;
    }

    public static List<QuestionEntity> createDummyQuetionList() {
        final List<QuestionEntity> questionEntityList = new ArrayList<>();
        questionEntityList.add(createDummyQuestion());
        return questionEntityList;
    }

    public static QuestionRequestDto createDummyQuestionRequest() {
        final QuestionRequestDto questionRequestDto = new QuestionRequestDto();
        questionRequestDto.setContentId(0L);
        questionRequestDto.setText("someText");
        return questionRequestDto;
    }

    public static TopicRequestDto createDummyTopicRequest() {
        final TopicRequestDto topicRequestDto = new TopicRequestDto();
        topicRequestDto.setEnrolledUserEntities(createDummyUserSet());
        topicRequestDto.setWikiDatumEntities(createDummyWikiDataSet());
        topicRequestDto.setContentEntityList(createDummyContentList());
        topicRequestDto.setId(0L);
        topicRequestDto.setDescription("someDescription");
        topicRequestDto.setImageUrl("someImageUrl");
        topicRequestDto.setTitle("someTitle");
        return topicRequestDto;
    }

    public static TopicResponseDto createDummyTopicResponse() {
        final TopicResponseDto topicResponseDto = new TopicResponseDto();
        topicResponseDto.setWikiDatumEntities(createDummyWikiDataSet());
        topicResponseDto.setContentEntityList(createDummyContentList());
        topicResponseDto.setId(0L);
        topicResponseDto.setDescription("someDescription");
        topicResponseDto.setImageUrl("someImageUrl");
        topicResponseDto.setTitle("someTitle");
        return topicResponseDto;
    }

    public static WikiDataEntity createDummyWikiData() {
        final WikiDataEntity wikiDataEntity = new WikiDataEntity();
        wikiDataEntity.setId("id");
        wikiDataEntity.setDescription("someDescription");
        wikiDataEntity.setConceptUri("someConceptUri");
        wikiDataEntity.setLabel("someLabel");
        return wikiDataEntity;
    }

    public static Set<WikiDataEntity> createDummyWikiDataSet() {
        final Set<WikiDataEntity> wikiDataEntitySet = new HashSet<>();
        wikiDataEntitySet.add(createDummyWikiData());
        return wikiDataEntitySet;
    }

    public static List<ContentEntity> createDummyContentList() {
        final List<ContentEntity> contentEntityList = new ArrayList<>();
        contentEntityList.add(createDummyContent());
        return contentEntityList;
    }

    public static UserEntity createDummyUser() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail("email");
        userEntity.setId(0L);
        userEntity.setName("name");
        userEntity.setPassword("pass");
        userEntity.setUsername("userName");
        return userEntity;
    }

    public static Set<UserEntity> createDummyUserSet() {
        final Set<UserEntity> userEntitySet = new HashSet<>();
        userEntitySet.add(createDummyUser());
        return userEntitySet;
    }

    public static UserPrincipal createDummyCurrentUser() {
        return UserPrincipal
                .create(UserEntity.builder().name("name").username("username").email("email").id(0L).password("pass")
                        .enrolledTopicEntities(new HashSet<>()).build());
    }

    public static EnrollmentRequestDto createDummyEnrollmentRequest() {
        final EnrollmentRequestDto enrollmentRequestDto = new EnrollmentRequestDto();
        enrollmentRequestDto.setTopicId(0L);
        enrollmentRequestDto.setUsername("username");
        return enrollmentRequestDto;
    }




    public static PublishRequestDto createDummyPublishRequest() {
        final PublishRequestDto publishRequestDto = new PublishRequestDto();
        publishRequestDto.setPublish(true);
        publishRequestDto.setTopicId(0L);
        return publishRequestDto;
    }

    public static LearningPathEntity createDummyLearningStep() {
        final LearningPathEntity learningPathEntity = new LearningPathEntity();
        learningPathEntity.setAnswerId(0L);
        learningPathEntity.setContentId(0L);
        learningPathEntity.setQuestionId(0L);
        learningPathEntity.setUserId(0L);
        learningPathEntity.setCreatedBy(0L);
        return learningPathEntity;
    }

    public static List<LearningPathEntity> createDummyLearningStepList() {
        final List<LearningPathEntity> learningPathEntities = new ArrayList<>();
        learningPathEntities.add(createDummyLearningStep());
        return learningPathEntities;
    }

    public static AnswerRequestDto createDummyAnswerRequest() {
        final AnswerRequestDto answerRequestDto = new AnswerRequestDto();
        answerRequestDto.setChoiceId(0L);
        answerRequestDto.setQuestionId(0L);
        return answerRequestDto;
    }

}
