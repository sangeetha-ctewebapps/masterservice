package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.ZonalEntity;
import com.lic.epgs.mst.entity.ZonalOffice;

public interface ZonalEntityRepository extends JpaRepository<ZonalEntity,Long> {

	
	@Query(value="SELECT ZONAL_ID,ZONAL_CODE,CREATED_BY,CREATED_ON,MODIFIED_ON,MODIFIED_BY,DESCRIPTION,IS_ACTIVE,IS_DELETED,PINCODE,CITY_NAME,DISTRICT_NAME,STATE_NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,EMAIL_ID,TELEPHONE_NO,TIN,GSTIN FROM Master_zonal order by ZONAL_ID ", nativeQuery = true)
	public List<ZonalEntity> getAllZonalOffice();
	
	
	@Query(value ="select mz.* from master_zonal mz,master_unit mu where mu.zonal_id = mz.zonal_id and mu.unit_code = :unitCode", nativeQuery = true)
	public ZonalEntity getAllZoneDetailsByUnitCode(@Param("unitCode")String unitCode);
	
	@Query(value ="select * from master_zonal where zonal_code = :zonalCode", nativeQuery = true)
	public ZonalEntity getZoneDetailsByZoneCode(@Param("zonalCode")String zonalCode);
	
}
