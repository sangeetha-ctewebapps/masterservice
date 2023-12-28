package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.CityEntity;

public interface CityEntityRepository extends JpaRepository<CityEntity,Long> {

	
	@Query(value="SELECT CITY_ID,DISTRICT_ID,CREATED_BY,CREATED_ON,MODIFIED_ON,MODIFIED_BY,DESCRIPTION,IS_ACTIVE,IS_DELETED,CITY_CODE FROM master_city order by CITY_ID,DISTRICT_ID ", nativeQuery = true)
	public List<CityEntity> findAllWithDistrictIds();
	
	@Query(value="SELECT * FROM (\r\n" + 
			"SELECT mc.*, row_number() over (ORDER BY mc.DESCRIPTION ASC) line_number\r\n" + 
			"FROM master_city mc\r\n" + 
			") WHERE line_number BETWEEN :start_t AND :end_t ORDER BY line_number", nativeQuery = true)
	public List<CityEntity> findAllWithDistrictIds(@Param ("start_t") Long start_t,@Param ("end_t") Long end_t);
	
	 @Query(value="SELECT * FROM master_city WHERE DISTRICT_ID=:districtId order by description ASC OFFSET :startAt ROWS FETCH NEXT 100 ROWS ONLY", nativeQuery = true)
	 public List<CityEntity> findAllByDistrictId(@Param("districtId") Long districtId, @Param("startAt") Long startAt);
	
	@Query(value="SELECT COUNT(*) FROM MASTER_CITY WHERE DISTRICT_ID=:districtId", nativeQuery = true)
    public int findCityCount(@Param("districtId") Long districtId);
	
	
	 @Query(value="select * from master_city mc,master_state ms,master_district md where mc.district_id = md.district_id and md.state_id = ms.state_id and ms.state_code =:stateCode order by mc.description ASC", nativeQuery = true)
	 public List<CityEntity> getAllCitiesByStateCode(@Param("stateCode") String stateCode);


	@Query(value ="SELECT * FROM MASTER_CITY WHERE DISTRICT_ID=:districtId", nativeQuery = true)
	public List<CityEntity> getCityByDistrictId(@Param("districtId") Long districtId);
}
