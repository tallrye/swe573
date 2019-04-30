package com.wlearn.backend.repository;

import com.wlearn.backend.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findById(Long topicId);

    Page<Topic> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    void deleteById(Long topicId);

    List<Topic> findByIdIn(List<Long> topicIds);

    List<Topic> findByIdIn(List<Long> topicIds, Sort sort);
}