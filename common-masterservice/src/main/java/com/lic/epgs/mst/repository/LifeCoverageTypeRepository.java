package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LifeCoverageTypeMst;

@Repository
public interface LifeCoverageTypeRepository extends JpaRepository<LifeCoverageTypeMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM MASTER_LIFE_COVERAGE_TYPE WHERE LIFE_COVERAGE_TYPE_CODE = :lifeCoverageTypeCode"
	 * , nativeQuery = true) Optional<LifeCoverageTypeMst> findByCode(String
	 * lifeCoverageTypeCode);
	 */
	
	@Query("FROM LifeCoverageTypeMst WHERE lifeCoverageTypeCode = :code")
	public Optional<LifeCoverageTypeMst> findByCode(String code);

}
