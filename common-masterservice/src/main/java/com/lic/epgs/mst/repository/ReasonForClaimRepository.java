package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ReasonForClaim;

@Repository
public interface ReasonForClaimRepository extends JpaRepository<ReasonForClaim, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM REASON_FOR_CLAIM WHERE REASON_FOR_CLAIM_CODE = :reasonForClaimCode"
	 * , nativeQuery = true) Optional<ReasonForClaim> findByCode(String
	 * reasonForClaimCode);
	 */
	
	@Query("FROM ReasonForClaim WHERE reasonCode = :code")
	public Optional<ReasonForClaim> findByCode(String code);

}
