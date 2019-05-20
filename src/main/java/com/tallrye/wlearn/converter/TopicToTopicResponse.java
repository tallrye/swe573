package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.controller.dto.response.TopicResponse;
import com.tallrye.wlearn.persistence.model.Topic;
import org.springframework.core.convert.converter.Converter;

public class TopicToTopicResponse implements Converter<Topic, TopicResponse> {

    @Override
    public TopicResponse convert(Topic source) {
        return TopicResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .imageUrl(source.getImageUrl())
                .contentList(source.getContentList())
                .createdBy(source.getCreatedBy())
                .createdByName(source.getCreatedByName())
                .creationDateTime(source.getCreatedAt())
                .wikiData(source.getWikiDataSet())
                .published(source.isPublished())
                .build();
    }
}
