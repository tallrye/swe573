package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.dto.QuestionRequestDto;
import com.tallrye.wlearn.entity.QuestionEntity;
import org.springframework.core.convert.converter.Converter;

public class QuestionRequestToQuestion implements Converter<QuestionRequestDto, QuestionEntity> {

    @Override
    public QuestionEntity convert(QuestionRequestDto source) {
        return QuestionEntity.builder()
                .text(source.getText())
                .build();
    }
}
