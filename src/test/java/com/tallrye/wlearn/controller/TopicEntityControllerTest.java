package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.EnrollmentRequestDto;
import com.tallrye.wlearn.dto.PublishRequestDto;
import com.tallrye.wlearn.dto.TopicRequestDto;
import com.tallrye.wlearn.service.TopicService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TopicEntityControllerTest extends AbstractEntityControllerTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private final TopicController sut = new TopicController(topicService);

    @Test
    public void getAllTopics() {
        //Test
        sut.getAllTopics(currentUser);
        //Verify
        verify(topicService, times(1)).getAllTopics(currentUser);
    }


    @Test
    public void getTopicsByUser() {
        //Test
        sut.getTopicsByUsername("someName", currentUser);
        //Verify
        verify(topicService, times(1)).getTopicsCreatedBy("someName", currentUser);
    }

    @Test
    public void getTopic() {
        //Test
        sut.getTopic(currentUser, 0L);
        //Verify
        verify(topicService, times(1)).getTopicById(0L, currentUser);
    }

    @Test
    public void createTopic() {
        //Prepare
        final TopicRequestDto request = TopicRequestDto.builder().contentEntityList(new ArrayList<>()).description("description")
                .id(0L).imageUrl("someUrl").title("title").wikiDatumEntities(new HashSet<>()).build();
        //Test
        sut.createTopic(currentUser, request);
        //Verify
        verify(topicService, times(1)).createTopic(currentUser, request);
    }

    @Test
    public void changeStatus() {
        //Prepare
        final PublishRequestDto request = PublishRequestDto.builder().publish(true).topicId(0L).build();
        //Test
        sut.changeStatus(currentUser, request);
        //Verify
        verify(topicService, times(1)).publishStatusUpdate(currentUser, request);
    }

    @Test
    public void deleteTopic() {
        //Test
        sut.deleteTopic(currentUser, 0L);
        //Verify
        verify(topicService, times(1)).deleteTopicById(0L, currentUser);
    }

    @Test
    public void enroll() {
        //Prepare
        final EnrollmentRequestDto request = EnrollmentRequestDto.builder().topicId(0L).username("username").build();
        //Test
        sut.enroll(currentUser, request);
        //Verify
        verify(topicService, times(1)).enrollToTopicByUsername(currentUser, request);
    }

    @Test
    public void getEnrolledTopics() {
        //Test
        sut.getEnrolledTopics(currentUser, 0L);
        //Verify
        verify(topicService, times(1)).getTopicsByEnrolledUserId(currentUser, 0L);
    }
}
