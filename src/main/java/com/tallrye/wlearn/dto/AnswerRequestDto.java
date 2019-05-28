package com.tallrye.wlearn.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestDto {

    @NonNull
    private Long questionId;

    @NonNull
    private Long choiceId;

}
