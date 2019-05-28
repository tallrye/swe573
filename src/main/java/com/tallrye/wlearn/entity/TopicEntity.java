package com.tallrye.wlearn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "topics")
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotBlank
    private String title;

    @Lob
    @NotBlank
    private String description;

    @Lob
    @NotBlank
    private String imageUrl;

    @Nullable
    private boolean published;

    @NotBlank
    private String createdByName;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topicEntity")
    private List<ContentEntity> contentEntityList;

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "topic_wikidata",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "wikidata_id"))
    private Set<WikiDataEntity> wikiDataEntitySet;

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "enrolled_users",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> enrolledUserEntities;

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


