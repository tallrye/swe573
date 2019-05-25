package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.controller.dto.request.ContentRequest;
import com.tallrye.wlearn.service.ContentService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ContentControllerTest extends AbstractEntityControllerTest {

    @Mock
    private ContentService contentService;

    @InjectMocks
    private final ContentController sut = new ContentController(contentService);

    @Test
    public void testCreateContentByTopicId() {
        //Prepare
        final ContentRequest request = ContentRequest.builder().id(0L).text("someText").title("title").topicId(0L)
                .build();
        //Test
        sut.createContent(currentUser, request);
        //Verify
        verify(contentService, times(1)).createContentByTopicId(currentUser, request);
    }

    @Test
    public void testGetContentById() {
        //Test
        sut.getContent(currentUser, 0L);
        //Verify
        verify(contentService, times(1)).getContentById(currentUser, 0L);
    }

    @Test
    public void testDeleteContentById() {
        //Test
        sut.deleteContent(currentUser, 0L);
        //Verify
        verify(contentService, times(1)).deleteContentById(currentUser, 0L);
    }
}
