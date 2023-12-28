package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.PinEntity;

public interface PinEntityRepository extends JpaRepository<PinEntity,Long> {

	
	@Query(value="SELECT PIN_ID,CITY_ID,PIN_CODE,CREATED_BY,CREATED_ON,MODIFIED_ON,MODIFIED_BY,DESCRIPTION,IS_ACTIVE,IS_DELETED FROM master_pin_code order by CITY_ID,PIN_ID ", nativeQuery = true)
	public List<PinEntity> findAllWithDistrictIds();
	
	@Query(value="SELECT * FROM (\r\n" + 
			"SELECT mpc.*, row_number() over (ORDER BY mpc.DESCRIPTION ASC) line_number\r\n" + 
			"FROM master_pin_code mpc\r\n" + 
			") WHERE line_number BETWEEN :start_t AND :end_t ORDER BY line_number", nativeQuery = true)
	public List<PinEntity> findAllWithDistrictIds(@Param ("start_t") Long start_t,@Param ("end_t") Long end_t);
	
}
