package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.CityMaster;

public interface CityRepository extends JpaRepository<CityMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_CITY WHERE CITY_CODE =:cityCode", nativeQuery = true)
	Optional<CityMaster> findByCode(String cityCode);
	
	public List<CityMaster> findByDistrict(@Param("id") long districtId);
	
	@Modifying	
	@Query(value = "UPDATE MASTER_CITY SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE CITY_ID= :cityId", nativeQuery = true) 
	public void deleteBycityId(@Param("cityId")Long cityId);

	
	@Query(value = "select * from MASTER_CITY where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	public List<CityMaster> findAllByCondition();

}
