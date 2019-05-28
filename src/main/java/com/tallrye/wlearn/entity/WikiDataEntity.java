package com.tallrye.wlearn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "wikidata")
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiDataEntity implements Serializable {

    @Id
    @NotBlank
    private String id;

    @Lob
    @NotBlank
    private String label;

    @Lob
    private String description;

    @Lob
    private String conceptUri;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

}
