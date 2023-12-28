package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.UnitCodeEntity;

@Repository
public interface UnitCodeRepository extends JpaRepository<UnitCodeEntity, Long> {
	
	@Query(value = "SELECT distinct MU.*,MS.STATE_CODE,MD.DISTRICT_CODE FROM MASTER_UNIT MU,master_city mc, MASTER_STATE MS, MASTER_DISTRICT MD  where" + 
			" lower(MU.DISTRICT_NAME)=lower(MD.DESCRIPTION) and lower(MU.STATE_NAME)=lower(MS.DESCRIPTION)" + 
			" and mc.DISTRICT_ID=MD.DISTRICT_ID and MD.STATE_ID=MS.STATE_ID and MU.UNIT_CODE=:unitCode", nativeQuery = true)
	public List<UnitCodeEntity> getDisCodeStatCodeByunitCode(@Param("unitCode") String unitCode);
	
	@Query(value = "select * from master_unit where UNIT_CODE=:unitCode", nativeQuery = true)
	public UnitCodeEntity getUnitDataByUnitCode(@Param("unitCode") String unitCode);
	

}
