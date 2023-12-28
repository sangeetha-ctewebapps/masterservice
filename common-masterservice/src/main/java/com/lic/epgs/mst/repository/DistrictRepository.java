package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.entity.District;
import com.lic.epgs.mst.entity.DistrictEntity;
import com.lic.epgs.mst.entity.DistrictStateResponseModel;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

	@Query("SELECT DISTINCT r FROM District r WHERE r.districtCode = :districtcode")
	public Optional<District> findByDistrictCode(String districtcode);
	
	public List<District> findByStates(@Param("id") long stateId);
	
	@Modifying	
	@Query(value = "UPDATE MASTER_DISTRICT SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE DISTRICT_ID= :districtId", nativeQuery = true) 
	public void deleteBydistrictId(@Param("districtId")Long districtId);

	
	@Query(value = "select * from MASTER_DISTRICT where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	public List<District> findAllByCondition();
	
}
