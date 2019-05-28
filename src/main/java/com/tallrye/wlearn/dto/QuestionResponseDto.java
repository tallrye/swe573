package com.tallrye.wlearn.dto;

import com.tallrye.wlearn.entity.ChoiceEntity;
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
public class QuestionResponseDto {

    private Long id;

    private String text;

    private List<ChoiceEntity> choiceEntityList;

    private ChoiceEntity userAnswer;

}
