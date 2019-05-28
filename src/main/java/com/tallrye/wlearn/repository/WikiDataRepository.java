package com.tallrye.wlearn.repository;

import com.tallrye.wlearn.entity.WikiDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiDataRepository extends JpaRepository<WikiDataEntity, String> {

}
