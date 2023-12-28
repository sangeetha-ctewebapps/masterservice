package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LeadSourceTypeMaster;

@Repository
public interface LeadSourceTypeRepository extends JpaRepository<LeadSourceTypeMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_LEAD_SOURCE_TYPE WHERE LEAD_SOURCE_TYPE_CODE = :leadSourceTypeCode", nativeQuery = true)
	Optional<LeadSourceTypeMaster> findByCode(String leadSourceTypeCode);

}
