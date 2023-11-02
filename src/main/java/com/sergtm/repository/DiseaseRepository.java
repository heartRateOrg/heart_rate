package com.sergtm.repository;

import com.sergtm.entities.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
	Disease findOneByName(String name);
}
