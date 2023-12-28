package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ClaimPaymentTypeMst;

@Repository
public interface ClaimPaymentTypeRepository extends JpaRepository<ClaimPaymentTypeMst, Long> {

	@Query(value = "SELECT * FROM MASTER_CLAIM_PAYMENT_TYPE WHERE CLAIM_PAYMENT_TYPE_CODE = :claimPaymentTypeCode", nativeQuery = true)
	Optional<ClaimPaymentTypeMst> findByCode(String claimPaymentTypeCode);

}