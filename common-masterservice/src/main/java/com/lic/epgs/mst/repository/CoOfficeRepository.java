package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CoOffice;

@Repository
public interface CoOfficeRepository extends JpaRepository<CoOffice, Long> {

	@Query("SELECT DISTINCT r FROM CoOffice r WHERE r.co_code = :cocode")
	public Object findByCoCode(String cocode);
	
	@Query( value = "select * FROM master_co_office where co_code = :loggedInUserUnitCode", nativeQuery = true)
	 public  CoOffice getDetailsByCoCode(@Param ("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	
	@Modifying
	@Query(value = "UPDATE MASTER_CO_OFFICE SET IS_ACTIVE = 'N' ,IS_DELETED ='Y' WHERE CO_ID=:coId", nativeQuery = true)
	Integer findDeletedById(@Param("coId") Long coId);
	
	
	@Modifying	
	@Query(value = "UPDATE MASTER_CO_OFFICE SET IS_ACTIVE = 'N' ,IS_DELETED ='Y' WHERE CO_ID=:coId", nativeQuery = true) 
	 public void deleteByCoId(@Param("coId")Long coId);

}
