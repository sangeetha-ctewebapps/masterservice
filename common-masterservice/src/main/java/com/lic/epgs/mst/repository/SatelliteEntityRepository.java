package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lic.epgs.mst.entity.SatelliteEntity;

public interface SatelliteEntityRepository extends JpaRepository<SatelliteEntity,Long> {

	
	@Query(value="SELECT SATELLITE_ID,UNIT_ID,SATELLITE_CODE,CREATED_BY,CREATED_ON,MODIFIED_ON,MODIFIED_BY,DESCRIPTION,IS_ACTIVE,IS_DELETED,PINCODE,CITY_NAME,DISTRICT_NAME,STATE_NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,EMAIL_ID,TELEPHONE_NO,TIN,GSTIN FROM master_satellite order by UNIT_ID,SATELLITE_ID ", nativeQuery = true)
	public List<SatelliteEntity> getAllSatelliteOfficeWithUnitIds();
	
}
