package com.wlearn.backend.util;

import com.wlearn.backend.model.Topic;
import com.wlearn.backend.model.User;
import com.wlearn.backend.payload.TopicResponse;
import com.wlearn.backend.payload.UserSummary;

public class ModelMapper {

    public static TopicResponse mapTopicToTopicResponse(Topic topic, User creator){
        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setId(topic.getId());
        topicResponse.setTitle(topic.getTitle());
        topicResponse.setDescription(topic.getDescription());
        topicResponse.setCreationDateTime(topic.getCreatedAt());
        topicResponse.setWikiData(topic.getWikiData());
        topicResponse.setContentList(topic.getContentList());

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        topicResponse.setCreatedBy(creatorSummary);

        return topicResponse;
    }
}
