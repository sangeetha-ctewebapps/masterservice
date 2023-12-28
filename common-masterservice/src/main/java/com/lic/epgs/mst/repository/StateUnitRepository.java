package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.StateUnitEntity;

@Repository
public interface StateUnitRepository extends JpaRepository<StateUnitEntity, Long>{
	
	   @Query(value = "SELECT * FROM ( select ms.* from master_unit mu, master_state ms where lower(mu.STATE_NAME) = lower(ms.DESCRIPTION) and mu.UNIT_CODE= :unitCode union select ms.* from master_state ms ,master_zonal mz where lower(mz.STATE_NAME) = lower(ms.DESCRIPTION) and mz.zonal_code = :unitCode)", nativeQuery = true)
	   public StateUnitEntity getStatesByCode(@Param ("unitCode")String unitCode);

}
