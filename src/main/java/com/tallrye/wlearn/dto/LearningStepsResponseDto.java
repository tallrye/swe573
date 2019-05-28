package com.tallrye.wlearn.dto;

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
public class LearningStepsResponseDto {

    private List<QuestionResponseDto> questions;

    private String contentTitle;

    private Long contentId;

    private Long nextContentId;

    private String topicTitle;

    private Long topicId;

}
