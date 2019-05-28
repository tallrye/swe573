package com.tallrye.wlearn.service;


import com.tallrye.wlearn.dto.EnrollmentRequestDto;
import com.tallrye.wlearn.dto.PublishRequestDto;
import com.tallrye.wlearn.dto.TopicRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.TopicResponseDto;
import com.tallrye.wlearn.entity.TopicEntity;
import com.tallrye.wlearn.exception.NotValidTopicException;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.repository.TopicRepository;
import com.tallrye.wlearn.repository.UserRepository;
import com.tallrye.wlearn.repository.WikiDataRepository;
import com.tallrye.wlearn.entity.UserEntity;
import com.tallrye.wlearn.entity.WikiDataEntity;
import com.tallrye.wlearn.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TopicService {

    private static final String TOPIC = "TopicEntity";

    private TopicRepository topicRepository;

    private UserRepository userRepository;

    private WikiDataRepository wikiDataRepository;

    private ConfigurableConversionService smepConversionService;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository,
                        WikiDataRepository wikiDataRepository, ConfigurableConversionService smepConversionService) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.smepConversionService = smepConversionService;
        this.wikiDataRepository = wikiDataRepository;
    }

    public ResponseEntity<List<TopicResponseDto>> getAllTopics(UserPrincipal currentUser) {
        return ResponseEntity.ok().body(topicRepository.findByPublished(true).stream()
                .map(topic -> smepConversionService.convert(topic, TopicResponseDto.class)).collect(
                        Collectors.toList()));
    }

    public ResponseEntity<List<TopicResponseDto>> getTopicsCreatedBy(String username, UserPrincipal currentUser) {
        final UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        return ResponseEntity.ok().body(topicRepository.findByCreatedBy(userEntity.getId()).stream()
                .map(topic -> smepConversionService.convert(topic, TopicResponseDto.class)).collect(
                        Collectors.toList()));
    }

    public ResponseEntity<TopicResponseDto> getTopicById(Long topicId, UserPrincipal currentUser) {
        final TopicEntity topicEntity = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException(TOPIC, "id", topicId.toString()));

        return ResponseEntity.ok().body(smepConversionService.convert(topicEntity, TopicResponseDto.class));
    }

    public ResponseEntity<TopicResponseDto> createTopic(UserPrincipal currentUser, TopicRequestDto topicRequestDto) {

        final List<WikiDataEntity> nonExistWikiDataEntitySet =
                topicRequestDto.getWikiDatumEntities() != null ? topicRequestDto.getWikiDatumEntities().stream()
                        .filter(wikiData -> !wikiDataRepository.existsById(wikiData.getId()))
                        .collect(Collectors.toList()) : null;

        wikiDataRepository.saveAll(nonExistWikiDataEntitySet);

        topicRepository.findById(topicRequestDto.getId())
                .ifPresent(topic -> topicRequestDto.setEnrolledUserEntities(topic.getEnrolledUserEntities()));

        topicRequestDto.setCreatedByName(currentUser.getUsername());

        final TopicEntity topicEntity = topicRepository.save(smepConversionService.convert(topicRequestDto, TopicEntity.class));

        return ResponseEntity.ok().body(smepConversionService.convert(topicEntity, TopicResponseDto.class));
    }

    public ResponseEntity<ApiResponseDto> publishStatusUpdate(UserPrincipal currentUser, PublishRequestDto publishRequestDto) {
        final TopicEntity topicEntity = topicRepository.findById(publishRequestDto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "id", publishRequestDto.getTopicId().toString()));

        WlearnUtils.checkCreatedBy(TOPIC, currentUser.getId(), topicEntity.getCreatedBy());

        if (publishRequestDto.isPublish()) {

            if(topicEntity.getContentEntityList() == null || topicEntity.getContentEntityList().isEmpty()){
                throw new NotValidTopicException(topicEntity.getTitle(),
                        "All topics must have at least one contentEntity. Please Check Your TopicEntity!");
            }

            topicEntity.getContentEntityList().forEach(content -> {
                if (content.getQuestionEntityList() == null || content.getQuestionEntityList().isEmpty()) {
                    throw new NotValidTopicException(topicEntity.getTitle(),
                            "All contents must have at least one questionEntity. Please Check Your Contents!");
                }
            });
        }

        topicEntity.setPublished(publishRequestDto.isPublish());
        topicRepository.save(topicEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "TopicEntity published successfully"));
    }

    public ResponseEntity<ApiResponseDto> deleteTopicById(Long topicId, UserPrincipal currentUser) {
        final TopicEntity topicEntity = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "id", topicId.toString()));

        WlearnUtils.checkCreatedBy(TOPIC, currentUser.getId(), topicEntity.getCreatedBy());

        topicRepository.delete(topicEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "TopicEntity deleted"));
    }

    public ResponseEntity<ApiResponseDto> enrollToTopicByUsername(UserPrincipal currentUser,
                                                                  EnrollmentRequestDto enrollmentRequestDto) {
        final TopicEntity topicEntity = topicRepository.findById(enrollmentRequestDto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "topicId",
                        enrollmentRequestDto.getTopicId().toString()));
        final UserEntity userEntity = userRepository.findByUsername(enrollmentRequestDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", enrollmentRequestDto.getUsername()));
        topicEntity.getEnrolledUserEntities().add(userEntity);
        topicRepository.save(topicEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "Enrolled to topicEntity successfully"));

    }

    public ResponseEntity<List<TopicResponseDto>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "id", userId.toString()));
        final List<TopicEntity> enrolledTopicEntities = topicRepository.findTopicByEnrolledUsersContainsAndPublished(userEntity, true);

        return ResponseEntity.ok()
                .body(enrolledTopicEntities.stream().map(topic -> smepConversionService.convert(topic, TopicResponseDto.class))
                        .collect(Collectors.toList()));
    }
}
