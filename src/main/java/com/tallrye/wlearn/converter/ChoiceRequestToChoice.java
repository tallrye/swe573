package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.controller.dto.request.ChoiceRequest;
import com.tallrye.wlearn.persistence.model.Choice;
import org.springframework.core.convert.converter.Converter;

public class ChoiceRequestToChoice implements Converter<ChoiceRequest, Choice> {

    @Override
    public Choice convert(ChoiceRequest source) {
        return Choice.builder()
                .text(source.getText())
                .correct(source.getCorrect())
                .build();
    }
}
