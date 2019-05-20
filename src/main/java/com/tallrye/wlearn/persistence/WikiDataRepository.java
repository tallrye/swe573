package com.tallrye.wlearn.persistence;

import com.tallrye.wlearn.persistence.model.WikiData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiDataRepository extends JpaRepository<WikiData, String> {

}
