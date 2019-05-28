package com.tallrye.wlearn.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@IdClass(LearningPathEntity.IdClass.class)
@Table(name = "learning_steps")
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPathEntity implements Serializable {

    @Id
    private Long userId;

    @Id
    private Long contentId;

    @Id
    private Long questionId;

    @Id
    private Long answerId;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Data
    static class IdClass implements Serializable {
        private Long userId;
        private Long contentId;
        private Long questionId;
        private Long answerId;
    }
}
