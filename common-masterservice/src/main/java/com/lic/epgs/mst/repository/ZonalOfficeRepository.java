package com.lic.epgs.mst.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.ZonalOffice;

public interface ZonalOfficeRepository extends JpaRepository<ZonalOffice, Long> {
	 
		@Query("SELECT DISTINCT r FROM ZonalOffice r WHERE r.zonalCode = :zonalcode")
		public Optional<ZonalOffice> findByZonalOfficeCode(String zonalcode);
		
		@Modifying
		@Query(value = "UPDATE MASTER_ZONAL SET IS_DELETED ='Y' WHERE ZONAL_ID=:zonalId", nativeQuery = true)
		Integer findDeletedById(long zonalId);
		
		@Query(value = "select * from MASTER_ZONAL where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
		  public List<ZonalOffice> findAllByCondition();
		
		@Query(value = "select ZONAL_CODE from MASTER_ZONAL where zonal_id = (select ZONAL_ID from master_unit where unit_code =:unitCode)", nativeQuery = true) 
		  public String getZoneForUnitCode(@Param("unitCode") String unitCode);
		
			@Query(value ="select count(*) from MASTER_ZONAL where ZONAL_CODE=:zonalCode", nativeQuery = true)
			public int checkIfZonalCodeIsValid(@Param("zonalCode")String zonalCode);
			
			@Query(value ="select *  from MASTER_ZONAL where ZONAL_CODE=:loggedInUserUnitCode", nativeQuery = true)
			public ZonalOffice getZonalOfficeDetails(@Param("loggedInUserUnitCode")String loggedInUserUnitCode);
			
			
			@Query(value = "select * from master_zonal where co_id =:coId", nativeQuery = true)
			public List<ZonalOffice> getAllZonesbasedOnCoId(@Param("coId") Long coId);
			
				
			
}
