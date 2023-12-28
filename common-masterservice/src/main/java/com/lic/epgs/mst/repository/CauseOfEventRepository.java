package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CauseOfEventEntity;
import com.lic.epgs.mst.entity.ReasonForClaim;

@Repository
public interface CauseOfEventRepository extends JpaRepository<CauseOfEventEntity , Long>{

	@Query(value= "SELECT * FROM MASTER_CAUSE_OF_EVENT ORDER BY DESCRIPTION", nativeQuery=true)
	List<CauseOfEventEntity> findAllCuaseOfEvent();
	
		
	//@Query("select * FROM MASTER_CAUSE_OF_EVENT WHERE CAUSE_OF_EVENT_CODE = :causeOfEventCode")
	//public CauseOfEventEntity findByCode(String causeCode);



}
