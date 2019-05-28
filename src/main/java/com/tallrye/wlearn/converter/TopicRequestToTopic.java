package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.dto.TopicRequestDto;
import com.tallrye.wlearn.entity.TopicEntity;
import org.springframework.core.convert.converter.Converter;

public class TopicRequestToTopic implements Converter<TopicRequestDto, TopicEntity> {

    @Override
    public TopicEntity convert(TopicRequestDto source) {

        final TopicEntity topicEntity = TopicEntity.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .wikiDataEntitySet(source.getWikiDatumEntities())
                .enrolledUserEntities(source.getEnrolledUserEntities())
                .imageUrl(source.getImageUrl())
                .createdByName(source.getCreatedByName())
                .build();

        if (source.getId() != 0L) {
            topicEntity.setId(source.getId());
        }

        return topicEntity;
    }
}
