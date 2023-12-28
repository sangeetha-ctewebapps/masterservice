package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CoverageType;

@Repository
public interface CoverageTypeRepository extends JpaRepository<CoverageType, Long> {

	@Query("SELECT DISTINCT r FROM CoverageType r WHERE r.coverage_code = :coveragecode")
	public Object findByCoverageCode(String coveragecode);

}
