package com.tallrye.wlearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiDataRequestDto {

    @NotBlank
    private String id;
    @NotBlank
    private String label;
    private String description;
    private String conceptUri;

}
