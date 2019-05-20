package com.tallrye.wlearn.converter;

import com.tallrye.wlearn.controller.dto.request.QuestionRequest;
import com.tallrye.wlearn.persistence.model.Question;
import org.springframework.core.convert.converter.Converter;

public class QuestionRequestToQuestion implements Converter<QuestionRequest, Question> {

    @Override
    public Question convert(QuestionRequest source) {
        return Question.builder()
                .text(source.getText())
                .build();
    }
}
