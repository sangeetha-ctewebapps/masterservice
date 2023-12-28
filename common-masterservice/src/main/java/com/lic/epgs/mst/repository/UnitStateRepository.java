package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.UnitEntity;
import com.lic.epgs.mst.entity.UnitStateEntity;

@Repository
public interface UnitStateRepository  extends JpaRepository<UnitStateEntity, Long>{
	
	@Query(value = "select mu.*,ms.state_code,ms.code from master_unit mu, master_state ms where   lower(mu.STATE_NAME) = lower(ms.DESCRIPTION) and ms.STATE_CODE = :stateCode", nativeQuery = true)
	 public List<UnitStateEntity> getAllUnitbasedOnStateCode(@Param("stateCode") String stateCode);
	
	
	 @Query(value ="select mu.*,ms.state_code,ms.code from master_unit mu, master_state ms , master_users mz where mz.location_code = mu.unit_code and mu.state_name = ms.description and mz.username = :username", nativeQuery = true)
		public UnitStateEntity getLoggedinuserdetailsSearch(@Param("username")String username);
	
	

}
