package com.tallrye.wlearn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.core.lang.Nullable;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "contents")
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotBlank
    private String title;

    @Lob
    @NotBlank
    private String text;

    @JsonIgnore
    @ManyToOne
    private TopicEntity topicEntity;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contentEntity")
    private List<QuestionEntity> questionEntityList;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

}
