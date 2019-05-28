package com.tallrye.wlearn.repository;

import com.tallrye.wlearn.entity.LearningPathEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LearningPathRepository extends JpaRepository<LearningPathEntity, Long> {

    Optional<LearningPathEntity> findByUserIdAndContentIdAndQuestionIdAndAnswerId(Long userId, Long contentId,
                                                                                  Long questionId, Long answerId);

    List<LearningPathEntity> findByUserIdAndContentId(Long userId, Long contentId);
}
