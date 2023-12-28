package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.DistrictEntity;

public interface DistrictEntityRepository extends JpaRepository<DistrictEntity,Long> {

	
	@Query(value="SELECT DISTRICT_ID,STATE_ID,DISTRICT_CODE,DESCRIPTION,IS_ACTIVE,IS_DELETED,CREATED_ON,CREATED_BY,MODIFIED_ON,MODIFIED_BY FROM master_district order by created_on desc ", nativeQuery = true)
	public List<DistrictEntity> findAllWithStateIds();
	
	@Query(value="SELECT * FROM master_district WHERE STATE_ID=:stateId ORDER BY DESCRIPTION ASC", nativeQuery = true)
	public List<DistrictEntity> findByStateId(@Param("stateId") String stateId);
	
	
}
