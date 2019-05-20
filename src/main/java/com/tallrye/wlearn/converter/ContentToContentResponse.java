package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.controller.dto.response.ContentResponse;
import com.tallrye.wlearn.persistence.model.Content;
import org.springframework.core.convert.converter.Converter;

public class ContentToContentResponse implements Converter<Content, ContentResponse> {

    @Override
    public ContentResponse convert(Content source) {
        return ContentResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .text(source.getText())
                .topicId(source.getTopic().getId())
                .topicTitle(source.getTopic().getTitle())
                .questionCount((long) (source.getQuestionList() != null ? source.getQuestionList().size() : 0))
                .build();
    }
}
