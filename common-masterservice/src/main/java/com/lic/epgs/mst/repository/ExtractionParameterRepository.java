package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ExtractionParameterMaster;

@Repository
public interface ExtractionParameterRepository extends JpaRepository<ExtractionParameterMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_EXTRACTION_PARAMETER WHERE EXTRACTION_PARAMETER_CODE =:extractionParameterCode", nativeQuery = true)
	Optional<ExtractionParameterMaster> findByCode(String extractionParameterCode);
}
