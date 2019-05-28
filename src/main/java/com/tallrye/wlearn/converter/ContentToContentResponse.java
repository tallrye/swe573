package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.dto.ContentResponseDto;
import com.tallrye.wlearn.entity.ContentEntity;
import org.springframework.core.convert.converter.Converter;

public class ContentToContentResponse implements Converter<ContentEntity, ContentResponseDto> {

    @Override
    public ContentResponseDto convert(ContentEntity source) {
        return ContentResponseDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .text(source.getText())
                .topicId(source.getTopicEntity().getId())
                .topicTitle(source.getTopicEntity().getTitle())
                .questionCount((long) (source.getQuestionEntityList() != null ? source.getQuestionEntityList().size() : 0))
                .build();
    }
}
