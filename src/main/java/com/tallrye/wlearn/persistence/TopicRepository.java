package com.tallrye.wlearn.persistence;

import com.tallrye.wlearn.persistence.model.Topic;
import com.tallrye.wlearn.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findById(Long topicId);

    List<Topic> findByCreatedBy(Long userId);

    List<Topic> findByPublished(Boolean published);

    long countByCreatedBy(Long userId);

    void deleteById(Long topicId);

    List<Topic> findTopicByEnrolledUsersContainsAndPublished(User user,Boolean published);
}
