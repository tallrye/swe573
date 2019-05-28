package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.dto.TopicResponseDto;
import com.tallrye.wlearn.entity.TopicEntity;
import org.springframework.core.convert.converter.Converter;

public class TopicToTopicResponse implements Converter<TopicEntity, TopicResponseDto> {

    @Override
    public TopicResponseDto convert(TopicEntity source) {
        return TopicResponseDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .imageUrl(source.getImageUrl())
                .contentEntityList(source.getContentEntityList())
                .createdBy(source.getCreatedBy())
                .createdByName(source.getCreatedByName())
                .creationDateTime(source.getCreatedAt())
                .wikiDatumEntities(source.getWikiDataEntitySet())
                .published(source.isPublished())
                .build();
    }
}
