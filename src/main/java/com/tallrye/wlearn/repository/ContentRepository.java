package com.tallrye.wlearn.persistence;

import com.tallrye.wlearn.entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

    Optional<ContentEntity> findById(Long contentId);

    List<ContentEntity> findByCreatedBy(Long userId);

    Long countByCreatedBy(Long userId);

    void deleteContentById(Long contentId);

}
