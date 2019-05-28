package com.tallrye.wlearn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "questions")
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Lob
    private String text;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionEntity")
    private List<ChoiceEntity> choiceEntityList;

    @JsonIgnore
    @ManyToOne
    private ContentEntity contentEntity;

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
