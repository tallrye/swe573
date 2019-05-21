package com.tallrye.wlearn.controller.dto.request;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequest {

    @NonNull
    private Long id = 0L;

    @NonNull
    private Long topicId;

    @NotBlank
    private String title;

    @NotBlank
    @Column(columnDefinition="TEXT")
    private String text;
}
