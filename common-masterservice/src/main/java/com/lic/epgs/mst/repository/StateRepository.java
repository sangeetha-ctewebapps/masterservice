package com.lic.epgs.mst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.entity.StateMaster;

@Repository
public interface StateRepository extends JpaRepository<StateMaster, Long> {

	public List<StateMaster> findByCountry(@Param("id") long countryId);

	Optional<StateMaster> findByStateCode(String stateCode);
	
	@Modifying
	@Query(value = "UPDATE MASTER_STATE SET IS_DELETED ='Y',IS_ACTIVE ='N' WHERE state_id=:stateId", nativeQuery = true)
	Integer findDeletedById(@Param ("stateId") Long stateId);
	
	@Query(value = "select * from MASTER_STATE where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	  public List<StateMaster> findAllByStates();
	
	@Query(value = "select * from MASTER_STATE where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	  public List<StateMaster> findAllByCondition();

	@Query("UPDATE StateMaster sm SET sm.isActive='N' WHERE sm.stateId = :id")
	@Transactional
	@Modifying
	void delete(Long id);

	@Transactional
	default void delete(StateMaster entity) {
		//System.out.println("i AM IN DELETED METHODS ");
		delete(entity.getStateId());
	}
	
	@Modifying	
	@Query(value = "UPDATE MASTER_STATE SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE STATE_ID= :stateId", nativeQuery = true) 
	public void deleteBystateId(@Param("stateId")Long stateId);


}
