package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.dto.ContentRequestDto;
import com.tallrye.wlearn.entity.ContentEntity;
import org.springframework.core.convert.converter.Converter;

public class ContentRequestToContent implements Converter<ContentRequestDto, ContentEntity> {

    @Override
    public ContentEntity convert(ContentRequestDto source) {
        final ContentEntity contentEntity = ContentEntity.builder()
                .title(source.getTitle())
                .text(source.getText())
                .build();

        if (source.getId() != 0L) {
            contentEntity.setId(source.getId());
        }

        return contentEntity;
    }
}
