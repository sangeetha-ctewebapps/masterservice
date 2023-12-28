package com.lic.epgs.mst.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.SatelliteOffice;

public interface SatelliteOfficeRepository extends JpaRepository<SatelliteOffice, Long> {
	 
		@Query("SELECT DISTINCT r FROM SatelliteOffice r WHERE r.satelliteCode = :satellitecode")
		public Optional<SatelliteOffice> findBySatelliteOfficeCode(String satellitecode);
		
		public List<SatelliteOffice> findByUnit(@Param("id") long unitId);
		
		@Modifying
		@Query(value = "UPDATE MASTER_SATELLITE SET IS_DELETED ='Y' WHERE SATELLITE_ID=:satelliteId", nativeQuery = true)
		Integer findDeletedById(long satelliteId);
		
		@Modifying	
		@Query(value = "UPDATE MASTER_SATELLITE SET IS_DELETED ='Y',IS_ACTIVE = 'N' WHERE SATELLITE_ID=:satelliteId", nativeQuery = true) 
		public void deleteBySateliteId(@Param("satelliteId")Long satelliteId);
		
		@Query(value = "select * from MASTER_SATELLITE where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
		  public List<SatelliteOffice> findAllByCondition();
		
		@Query(value = "select * from MASTER_SATELLITE WHERE SATELLITE_CODE = :satellitecode", nativeQuery = true) 
		  public SatelliteOffice findAllSateliteDetails(@Param("satellitecode") String satellitecode);
}
