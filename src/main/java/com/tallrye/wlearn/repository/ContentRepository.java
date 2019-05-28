package com.tallrye.wlearn.repository;

import com.tallrye.wlearn.entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Long> {

    Optional<ContentEntity> findById(Long contentId);


}
