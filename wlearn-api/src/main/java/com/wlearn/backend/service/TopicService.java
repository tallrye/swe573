package com.wlearn.backend.service;

import com.wlearn.backend.exception.BadRequestException;
import com.wlearn.backend.exception.ResourceNotFoundException;
import com.wlearn.backend.model.Topic;
import com.wlearn.backend.model.User;
import com.wlearn.backend.payload.PagedResponse;
import com.wlearn.backend.payload.TopicRequest;
import com.wlearn.backend.payload.TopicResponse;
import com.wlearn.backend.repository.TopicRepository;
import com.wlearn.backend.repository.UserRepository;
import com.wlearn.backend.security.UserPrincipal;
import com.wlearn.backend.util.AppConstants;
import com.wlearn.backend.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    public PagedResponse<TopicResponse> getAllTopics(UserPrincipal currentUser, int page, int size){

        validatePageNumberAndSize(page, size);

        // Retrieve Topics
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Topic> topics = topicRepository.findAll(pageable);

        if(topics.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), topics.getNumber(),
                    topics.getSize(), topics.getTotalElements(), topics.getTotalPages(), topics.isLast());
        }

        // Map Topics to TopicResponses containing topic creator details
        List<Long> topicIds = topics.map(Topic::getId).getContent();
        Map<Long, User> creatorMap = getTopicCreatorMap(topics.getContent());


        List<TopicResponse> topicResponses = topics.map(topic -> {
            return ModelMapper.mapTopicToTopicResponse(topic, creatorMap.get(topic.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(topicResponses, topics.getNumber(),
                topics.getSize(), topics.getTotalElements(), topics.getTotalPages(), topics.isLast());
    }

    public PagedResponse<TopicResponse> getTopicsCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all topics created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Topic> topics = topicRepository.findByCreatedBy(user.getId(), pageable);

        if (topics.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), topics.getNumber(),
                    topics.getSize(), topics.getTotalElements(), topics.getTotalPages(), topics.isLast());
        }

        // Map Topics to TopicResponses containing topic creator details
        List<Long> topicIds = topics.map(Topic::getId).getContent();

        List<TopicResponse> topicResponses = topics.map(topic -> {
            return ModelMapper.mapTopicToTopicResponse(topic, user);
        }).getContent();

        return new PagedResponse<>(topicResponses, topics.getNumber(),
                topics.getSize(), topics.getTotalElements(), topics.getTotalPages(), topics.isLast());
    }

    public Topic createTopic(TopicRequest topicRequest) {
        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());

        return topicRepository.save(topic);
    }

    public TopicResponse getTopicById(Long topicId, UserPrincipal currentUser) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "id", topicId));

        // Retrieve topic creator details
        User creator = userRepository.findById(topic.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", topic.getCreatedBy()));

        return ModelMapper.mapTopicToTopicResponse(topic, creator);
    }

    public boolean deleteTopicById(Long topicId, UserPrincipal currentUser){
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            topicRepository.deleteById(topicId);
            return true;
        }
        return false;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    Map<Long, User> getTopicCreatorMap(List<Topic> topics) {
        // Get Poll Creator details of the given list of polls
        List<Long> creatorIds = topics.stream()
                .map(Topic::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }



}
