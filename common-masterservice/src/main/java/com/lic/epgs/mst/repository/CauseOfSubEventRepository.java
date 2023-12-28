package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CauseOfSubEventEntity;

@Repository
public interface CauseOfSubEventRepository extends JpaRepository<CauseOfSubEventEntity, Long> {

	@Query(value = "select * from MASTER_CAUSE_OF_SUB_EVENT where SUB_CAUSE_INDICATOR =:subCauseIndicator ORDER BY SUB_CAUSE_NAME", nativeQuery = true)
	public List<CauseOfSubEventEntity> getAllCauseOfSubEvent(@Param("subCauseIndicator") String subCauseIndicator );

}
