package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.ContentRequestDto;
import com.tallrye.wlearn.service.ContentService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ContentEntityControllerTest extends AbstractEntityControllerTest {

    @Mock
    private ContentService contentService;

    @InjectMocks
    private final ContentController sut = new ContentController(contentService);

    @Test
    public void createContent() {
        //Prepare
        final ContentRequestDto request = ContentRequestDto.builder().id(0L).text("someText").title("title").topicId(0L)
                .build();
        //Test
        sut.createContent(currentUser, request);
        //Verify
        verify(contentService, times(1)).createContentByTopicId(currentUser, request);
    }

    @Test
    public void getContent() {
        //Test
        sut.getContent(currentUser, 0L);
        //Verify
        verify(contentService, times(1)).getContentById(currentUser, 0L);
    }

    @Test
    public void deleteContent() {
        //Test
        sut.deleteContent(currentUser, 0L);
        //Verify
        verify(contentService, times(1)).deleteContentById(currentUser, 0L);
    }
}
