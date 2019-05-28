package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.dto.ChoiceRequestDto;
import com.tallrye.wlearn.entity.ChoiceEntity;
import org.springframework.core.convert.converter.Converter;

public class ChoiceRequestToChoice implements Converter<ChoiceRequestDto, ChoiceEntity> {

    @Override
    public ChoiceEntity convert(ChoiceRequestDto source) {
        return ChoiceEntity.builder()
                .text(source.getText())
                .correct(source.getCorrect())
                .build();
    }
}
