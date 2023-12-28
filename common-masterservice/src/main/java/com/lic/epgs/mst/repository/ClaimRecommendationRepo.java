package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ClaimRecommendation;

@Repository
public interface ClaimRecommendationRepo extends JpaRepository<ClaimRecommendation, Long> {

	@Query(value = "SELECT * FROM MASTER_CLAIM_RECOMMENDATION WHERE CLAIM_CODE = :claimCode", nativeQuery = true)
	public Optional<ClaimRecommendation> findByClaimCode(String claimCode);

}
