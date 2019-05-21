package com.tallrye.wlearn.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Table(name = "contents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content extends UserCreatedDataBaseEntity {

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
    private Topic topic;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
    private List<Question> questionList;

}
