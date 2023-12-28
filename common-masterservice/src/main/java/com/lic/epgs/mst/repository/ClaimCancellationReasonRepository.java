package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ClaimTypeMst;

@Repository
public interface ClaimCancellationReasonRepository extends JpaRepository<ClaimTypeMst, Long> {

	@Query(value = "SELECT * FROM MASTER_CLAIM_CANCELLATION WHERE CLAIM_TYPE_CODE = :claimTypeCode", nativeQuery = true)
	Optional<ClaimTypeMst> findByCode(String claimTypeCode);
}
