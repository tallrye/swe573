package com.tallrye.wlearn.repository;

import com.tallrye.wlearn.entity.ChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<ChoiceEntity, Long> {

}
