package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LockPeriodWhileInServiceMst;

@Repository
public interface LockPeriodWhileInServiceRepository extends JpaRepository<LockPeriodWhileInServiceMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM LOCK_PERIOD_WHILE_IN_SERVICE WHERE LOCK_PERIOD_WHILE_IN_SERVICE_CODE = :lockPeriodWhileInServiceCode"
	 * , nativeQuery = true) Optional<LockPeriodWhileInServiceMst> findByCode(String
	 * lockPeriodWhileInServiceCode);
	 */
	
	@Query("FROM LockPeriodWhileInServiceMst WHERE lockPeriodWhileInServiceCode = :code")
	public Optional<LockPeriodWhileInServiceMst> findByCode(String code);

}
