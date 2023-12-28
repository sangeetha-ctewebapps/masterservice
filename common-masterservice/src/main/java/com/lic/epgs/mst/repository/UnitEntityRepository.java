package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.entity.UnitEntity;

public interface UnitEntityRepository extends JpaRepository<UnitEntity,Long> {

	
	 @Query(value="SELECT * FROM master_unit order by DESCRIPTION ", nativeQuery = true)
	  public List<UnitEntity> findAllWithZonalIds();

	@Query(value ="select * from master_unit where UNIT_CODE=:unitCode", nativeQuery = true)
	public UnitEntity getServiceDetailsByUnitCode(@Param("unitCode")String unitCode);	
	
	@Query(value = "select UN.*, MU.LOCATION_TYPE from master_users MU, "
            +"master_unit UN where MU.LOCATION_CODE = UN.UNIT_CODE AND MU.SRNUMBER=:srNumber", nativeQuery = true)
    public List<UnitEntity> getUnitDetails(@Param("srNumber") String srNumber);
	

	 @Query(value = "select MU.* from MASTER_UNIT MU, MASTER_ZONAL ZO where MU.ZONAL_ID=ZO.ZONAL_ID AND ZO.ZONAL_CODE =:locationCode order by MU.description asc", nativeQuery = true)
	 public List<UnitEntity> getAllUoForZoLocation(@Param("locationCode") String locationCode);
	 
	 
	 @Query(value ="select count(*) from master_unit where UNIT_CODE=:unitCode", nativeQuery = true)
		public int checkIfUnitCodeIsValid(@Param("unitCode")String unitCode);	
	 
	 
	
}
