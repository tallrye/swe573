package com.tallrye.wlearn.service.implementation;

import com.tallrye.wlearn.dto.ContentRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.ContentResponseDto;
import com.tallrye.wlearn.entity.ContentEntity;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.persistence.ContentRepository;
import com.tallrye.wlearn.persistence.TopicRepository;
import com.tallrye.wlearn.entity.TopicEntity;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.util.WlearnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContentServiceImpl {

    private static final String TOPIC = "TopicEntity";
    private static final String CONTENT = "ContentEntity";

    private ContentRepository contentRepository;

    private TopicRepository topicRepository;

    private ConfigurableConversionService smepConversionService;

    public ContentServiceImpl(ContentRepository contentRepository, TopicRepository topicRepository,
            ConfigurableConversionService smepConversionService) {
        this.contentRepository = contentRepository;
        this.topicRepository = topicRepository;
        this.smepConversionService = smepConversionService;
    }

    public ResponseEntity<ApiResponseDto> createContentByTopicId(UserPrincipal currentUser,
                                                                 ContentRequestDto contentRequestDto) {

        final TopicEntity topicEntity = topicRepository.findById(contentRequestDto.getTopicId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(TOPIC, "id", contentRequestDto.getTopicId().toString()));

        WlearnUtils.checkCreatedBy(TOPIC, currentUser.getId(), topicEntity.getCreatedBy());

        final ContentEntity contentEntity = smepConversionService.convert(contentRequestDto, ContentEntity.class);
        contentEntity.setTopicEntity(topicEntity);
        contentRepository.save(contentEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "ContentEntity created successfully"));
    }

    public ResponseEntity<ContentResponseDto> getContentById(UserPrincipal currentUser, Long contentId) {

        final ContentEntity contentEntity = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException(CONTENT, "id", contentId.toString()));

        final ContentResponseDto contentResponseDto = smepConversionService.convert(contentEntity, ContentResponseDto.class);

        final AtomicLong nextContentId = new AtomicLong(0L);

        contentEntity.getTopicEntity().getContentEntityList().stream()
                .map(ContentEntity::getId).collect(Collectors.toList()).stream().filter(id -> id > contentId).min(
                Comparator.comparing(Long::valueOf)).ifPresent(nextContentId::set);

        Objects.requireNonNull(contentResponseDto).setNextContentId(nextContentId.get());

        return ResponseEntity.ok().body(contentResponseDto);
    }

    public ResponseEntity<ApiResponseDto> deleteContentById(UserPrincipal currentUser, Long contentId) {

        final ContentEntity contentEntity = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException(CONTENT, "id", contentId.toString()));

        WlearnUtils.checkCreatedBy(CONTENT, currentUser.getId(), contentEntity.getCreatedBy());

        contentRepository.delete(contentEntity);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "ContentEntity deleted successfully"));
    }

}
