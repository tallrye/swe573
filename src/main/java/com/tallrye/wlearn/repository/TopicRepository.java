package com.tallrye.wlearn.persistence;

import com.tallrye.wlearn.entity.TopicEntity;
import com.tallrye.wlearn.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

    Optional<TopicEntity> findById(Long topicId);

    List<TopicEntity> findByCreatedBy(Long userId);

    List<TopicEntity> findByPublished(Boolean published);

    long countByCreatedBy(Long userId);

    void deleteById(Long topicId);

    List<TopicEntity> findTopicByEnrolledUsersContainsAndPublished(UserEntity userEntity, Boolean published);
}
