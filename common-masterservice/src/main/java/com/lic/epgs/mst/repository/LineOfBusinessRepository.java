package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.entity.LineOfBusinessMaster;

@Repository
public interface LineOfBusinessRepository extends JpaRepository<LineOfBusinessMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_LINE_BUSINESS WHERE LINE_BUSINESS_CODE = :lineBusinessCode", nativeQuery = true)
	Optional<LineOfBusinessMaster> findByCode(String lineBusinessCode);
	
	@Modifying
	@Query(value = "UPDATE MASTER_LINE_BUSINESS SET IS_DELETED ='Y' WHERE LINE_BUSINESS_ID =:lineBusinessId", nativeQuery = true)
	Integer findDeletedById(long lineBusinessId);
	
	@Query(value = "select * from MASTER_LINE_BUSINESS where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	public List<LineOfBusinessMaster> findAllByCondition();

}
