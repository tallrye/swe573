package com.tallrye.wlearn.service.implementation;

import com.tallrye.wlearn.dto.AnswerRequestDto;
import com.tallrye.wlearn.dto.QuestionRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.LearningStepsResponseDto;
import com.tallrye.wlearn.dto.QuestionResponseDto;
import com.tallrye.wlearn.entity.LearningPathEntity;
import com.tallrye.wlearn.entity.QuestionEntity;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.persistence.ContentRepository;
import com.tallrye.wlearn.persistence.LearningPathRepository;
import com.tallrye.wlearn.persistence.QuestionRepository;
import com.tallrye.wlearn.entity.ChoiceEntity;
import com.tallrye.wlearn.entity.ContentEntity;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.util.WlearnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionServiceImpl {

    private static final String CONTENT = "ContentEntity";
    private static final String QUESTION = "QuestionEntity";

    private QuestionRepository questionRepository;

    private ContentRepository contentRepository;

    private ConfigurableConversionService smepConversionService;

    private LearningPathRepository learningPathRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, ContentRepository contentRepository,
            ConfigurableConversionService smepConversionService, LearningPathRepository learningPathRepository) {
        this.questionRepository = questionRepository;
        this.contentRepository = contentRepository;
        this.smepConversionService = smepConversionService;
        this.learningPathRepository = learningPathRepository;
    }

    public ResponseEntity<ApiResponseDto> createQuestionByContentId(UserPrincipal currentUser,
                                                                    QuestionRequestDto questionRequestDto) {

        final ContentEntity contentEntity = contentRepository.findById(questionRequestDto.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException(CONTENT, "id",
                        questionRequestDto.getContentId().toString()));

        WlearnUtils.checkCreatedBy(CONTENT, currentUser.getId(), contentEntity.getCreatedBy());

        final QuestionEntity questionEntity = smepConversionService.convert(questionRequestDto, QuestionEntity.class);
        questionEntity.setContentEntity(contentEntity);
        questionRepository.save(questionEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "QuestionEntity created successfully"));
    }

    public ResponseEntity<ApiResponseDto> deleteQuestionById(Long questionId, UserPrincipal currentUser) {

        final QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION, "id", questionId.toString()));

        WlearnUtils.checkCreatedBy(QUESTION, currentUser.getId(), questionEntity.getCreatedBy());

        questionRepository.delete(questionEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "QuestionEntity deleted successfully"));
    }

    public ResponseEntity<LearningStepsResponseDto> getLearningSteps(UserPrincipal currentUser, Long contentId) {

        final List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        final AtomicReference<String> contentTitle = new AtomicReference<>();
        final AtomicReference<String> topicTitle = new AtomicReference<>();
        final AtomicReference<Long> topicId = new AtomicReference<>();
        final AtomicReference<Long> nextContentId = new AtomicReference<>();

        contentRepository.findById(contentId)
                .ifPresent(content -> {
                    content.getTopicEntity().getContentEntityList().stream()
                            .map(ContentEntity::getId).collect(Collectors.toList()).stream().filter(id -> id > contentId).min(
                            Comparator.comparing(Long::valueOf)).ifPresent(nextContentId::set);
                    final List<LearningPathEntity> learningPathEntities = learningPathRepository
                            .findByUserIdAndContentId(currentUser.getId(), contentId);
                    final List<QuestionEntity> questionEntityList = content.getQuestionEntityList();
                    if (questionEntityList != null) {
                        for (QuestionEntity questionEntity : questionEntityList) {
                            final QuestionResponseDto resp = new QuestionResponseDto();
                            final List<ChoiceEntity> choiceEntityList = questionEntity.getChoiceEntityList();
                            resp.setChoiceEntityList(choiceEntityList);
                            resp.setId(questionEntity.getId());
                            resp.setText(questionEntity.getText());
                            learningPathEntities.stream()
                                    .filter(learningStep -> learningStep.getQuestionId().equals(questionEntity.getId()))
                                    .findAny()
                                    .ifPresent(step -> choiceEntityList.stream()
                                            .filter(choice -> choice.getId().equals(step.getAnswerId())).findFirst()
                                            .ifPresent(
                                                    resp::setUserAnswer));
                            questionResponseDtoList.add(resp);
                        }
                    }
                    contentTitle.set(content.getTitle());
                    topicTitle.set(content.getTopicEntity().getTitle());
                    topicId.set(content.getTopicEntity().getId());
                });

        return ResponseEntity.ok()
                .body(LearningStepsResponseDto.builder().questions(questionResponseDtoList).contentId(contentId)
                        .topicId(topicId.get()).contentTitle(contentTitle.get())
                        .topicTitle(topicTitle.get()).nextContentId(nextContentId.get()).build());
    }

    public ResponseEntity<ApiResponseDto> giveAnswer(UserPrincipal currentUser, AnswerRequestDto answerRequestDto) {

        final QuestionEntity questionEntity = questionRepository.findById(answerRequestDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION, "id", answerRequestDto.getQuestionId().toString()));

        final ContentEntity contentEntity = questionEntity.getContentEntity();

        final LearningPathEntity learningPathEntity = learningPathRepository
                .findByUserIdAndContentIdAndQuestionIdAndAnswerId(currentUser.getId(), contentEntity.getId(),
                        answerRequestDto.getQuestionId(),
                        answerRequestDto.getChoiceId()).orElse(learningPathRepository
                        .save(LearningPathEntity.builder().userId(currentUser.getId())
                                .answerId(answerRequestDto.getChoiceId())
                                .questionId(answerRequestDto.getQuestionId()).contentId(contentEntity.getId()).build()));

        return ResponseEntity.ok().body(new ApiResponseDto(true,
                "For UserEntity " + learningPathEntity.getUserId() + " LearningPathEntity is created successfully"));
    }
}
