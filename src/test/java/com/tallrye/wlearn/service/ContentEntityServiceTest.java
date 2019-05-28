package com.tallrye.wlearn.service;

import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.dto.ContentRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.ContentResponseDto;
import com.tallrye.wlearn.entity.ContentEntity;
import com.tallrye.wlearn.entity.TopicEntity;
import com.tallrye.wlearn.exception.CreatedByException;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.repository.ContentRepository;
import com.tallrye.wlearn.repository.TopicRepository;
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
import static org.mockito.Mockito.when;

public class ContentEntityServiceTest extends AbstractServiceTest {


    @Mock
    private ContentRepository contentRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private ConfigurableConversionService smepConversionService;

    @InjectMocks
    private final ContentService sut = new ContentService(contentRepository, topicRepository,
            smepConversionService);

    @Test(expected = ResourceNotFoundException.class)
    public void createContent_ResourceNotFound() {
        //Prepare
        final ContentRequestDto request = TestUtils.createDummyContentRequest();
        when(topicRepository.findById(request.getTopicId())).thenReturn(Optional.empty());

        //Test
        sut.createContentByTopicId(currentUser, request);
    }

    @Test(expected = CreatedByException.class)
    public void createContent_CreateBy() {
        //Prepare
        final ContentRequestDto request = TestUtils.createDummyContentRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        topicEntity.setCreatedBy(1L);
        when(topicRepository.findById(request.getTopicId())).thenReturn(Optional.of(topicEntity));

        //Test
        sut.createContentByTopicId(currentUser, request);
    }

    @Test
    public void createChoice_Success() {
        //Prepare
        final ContentRequestDto request = TestUtils.createDummyContentRequest();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        final ContentEntity contentEntity = TestUtils.createDummyContent();
        topicEntity.setCreatedBy(currentUser.getId());
        when(topicRepository.findById(request.getTopicId())).thenReturn(Optional.of(topicEntity));
        when(smepConversionService.convert(request, ContentEntity.class)).thenReturn(contentEntity);

        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.createContentByTopicId(currentUser, request);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getContent_ResourceNotFound() {
        //Prepare
        when(contentRepository.findById(0L)).thenReturn(Optional.empty());

        //Test
        sut.getContentById(currentUser, 0L);
    }

    @Test
    public void getContent_Success() {
        //Prepare
        final ContentEntity contentEntity = TestUtils.createDummyContent();
        final List<ContentEntity> contentEntityList = new ArrayList<>();
        final TopicEntity topicEntity = TestUtils.createDummyTopic();
        contentEntityList.add(contentEntity);
        contentEntity.setTopicEntity(topicEntity);
        topicEntity.setContentEntityList(contentEntityList);
        final ContentResponseDto contentResponseDto = TestUtils.createDummyContentResponse();
        when(contentRepository.findById(0L)).thenReturn(Optional.of(contentEntity));
        when(smepConversionService.convert(contentEntity, ContentResponseDto.class)).thenReturn(contentResponseDto);

        //Test
        final ResponseEntity<ContentResponseDto> responseEntity = sut.getContentById(currentUser, 0L);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), contentResponseDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteContent_ResourceNotFound() {
        //Prepare
        when(contentRepository.findById(0L)).thenReturn(Optional.empty());

        //Test
        sut.deleteContentById(currentUser, 0L);
    }


    @Test(expected = CreatedByException.class)
    public void deleteContent_CreateBy() {
        //Prepare
        final ContentEntity contentEntity = TestUtils.createDummyContent();
        contentEntity.setCreatedBy(1L);
        when(contentRepository.findById(0L)).thenReturn(Optional.of(contentEntity));

        //Test
        sut.deleteContentById(currentUser, 0L);
    }

    @Test
    public void deleteContent_Success() {
        //Prepare
        final ContentEntity contentEntity = TestUtils.createDummyContent();
        contentEntity.setCreatedBy(currentUser.getId());
        when(contentRepository.findById(0L)).thenReturn(Optional.of(contentEntity));

        //Test
        final ResponseEntity<ApiResponseDto> responseEntity = sut.deleteContentById(currentUser, 0L);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }

}
