package com.tallrye.wlearn.persistence;

import com.tallrye.wlearn.entity.WikiDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiDataRepository extends JpaRepository<WikiDataEntity, String> {

}
