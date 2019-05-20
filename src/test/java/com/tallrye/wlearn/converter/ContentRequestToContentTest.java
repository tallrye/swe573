package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.controller.dto.request.ContentRequest;
import com.tallrye.wlearn.persistence.model.Content;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ContentRequestToContentTest {

    @InjectMocks
    private ContentRequestToContent sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertSuccessfully() {
        //Prepare
        final ContentRequest source = TestUtils.createDummyContentRequest();
        source.setId(1L);
        //Test
        final Content content = sut.convert(source);
        //Verify
        assertNotNull(content);
        assertEquals(content.getTitle(), source.getTitle());
        assertEquals(content.getText(), source.getText());
        assertNull(content.getTopic());
        assertNull(content.getQuestionList());
    }

}
