package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.MergerTypeMaster;

@Repository
public interface MergerTypeRepository extends JpaRepository<MergerTypeMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_MERGER_TYPE WHERE MERGER_TYPE_CODE = :mergerTypeCode", nativeQuery = true)
	Optional<MergerTypeMaster> findByCode(String mergerTypeCode);

}
