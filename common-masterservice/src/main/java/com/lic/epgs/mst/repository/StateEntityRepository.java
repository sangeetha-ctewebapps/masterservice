package com.lic.epgs.mst.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lic.epgs.mst.entity.StateMasterEntity;

@Repository
public interface StateEntityRepository extends JpaRepository<StateMasterEntity, Long> {
	
	
	 @Query(value="SELECT * FROM MASTER_STATE ORDER BY description", nativeQuery = true)
	 public List<StateMasterEntity> getAllStates();
	 
	 
	 @Query(value="select * from master_state where lower (description) = lower(:stateName)", nativeQuery = true)
	 public List<StateMasterEntity> getStateByStateName(@Param("stateName") String stateName);
	 

	
}
