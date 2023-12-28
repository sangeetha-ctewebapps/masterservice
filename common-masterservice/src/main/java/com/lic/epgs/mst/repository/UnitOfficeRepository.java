package com.lic.epgs.mst.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.entity.ZonalOffice;

public interface UnitOfficeRepository extends JpaRepository<UnitOffice, Long> {
	 
		@Query(value = "SELECT * FROM MASTER_UNIT  WHERE unit_code = :unitcode", nativeQuery = true)
		public UnitOffice findByUnitOfficeCode(@Param("unitcode")String unitcode);
		
		public List<UnitOffice> findByZonal(@Param("id") long zonalId);
		
		@Query(value = "select * from MASTER_UNIT where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
		  public List<UnitOffice> findAllByCondition();
		
		@Modifying
		@Query(value = "UPDATE MASTER_UNIT SET IS_DELETED ='Y' WHERE UNIT_ID=:unitId", nativeQuery = true)
		Integer findDeletedById(long unitId);
		
		
		@Query(value = "select * from master_unit where unit_code = :loggedInUserUnitCode", nativeQuery = true)
		public UnitOffice getAllUnitDetails(@Param("loggedInUserUnitCode")String loggedInUserUnitCode);
		
		
		@Query(value = "select * from master_unit where zonal_id=:zonalId", nativeQuery = true) 
		  public List<UnitOffice> getUnnitsFromZonalCode(@Param("zonalId") Long zonalId);
}
