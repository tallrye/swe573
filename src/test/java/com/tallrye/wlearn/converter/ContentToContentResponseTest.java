package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.controller.dto.response.ContentResponse;
import com.tallrye.wlearn.persistence.model.Content;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ContentToContentResponseTest {

    @InjectMocks
    private ContentToContentResponse sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertSuccessfully() {
        //Prepare
        final Content source = TestUtils.createDummyContent();
        //Test
        final ContentResponse contentResponse = sut.convert(source);
        //Verify
        assertNotNull(contentResponse);
        assertEquals(contentResponse.getId(), source.getId());
        assertEquals(contentResponse.getText(),source.getText());
        assertEquals(contentResponse.getTitle(),source.getTitle());
        assertEquals(contentResponse.getTopicId(),source.getTopic().getId());
    }
}
