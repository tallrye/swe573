package com.tallrye.wlearn.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRequestDto {

    @NonNull
    private Long questionId;

    @NotBlank
    @Size(max = 255)
    private String text;

    @NotNull
    private Boolean correct;

}
