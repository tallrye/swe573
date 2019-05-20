package com.tallrye.wlearn.controller.dto.response;

import com.tallrye.wlearn.persistence.model.Choice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    private Long id;

    private String text;

    private List<Choice> choiceList;

    private Choice userAnswer;

}
