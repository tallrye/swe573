package com.tallrye.wlearn.dto;

import com.tallrye.wlearn.entity.ContentEntity;
import com.tallrye.wlearn.entity.WikiDataEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponseDto {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Long createdBy;
    private String createdByName;
    private Instant creationDateTime;
    private Set<WikiDataEntity> wikiDatumEntities;
    private List<ContentEntity> contentEntityList;
    private boolean published;

}
