package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.AnnuityPayoutMethodMst;

@Repository
public interface AnnuityPayoutMethodRepository extends JpaRepository<AnnuityPayoutMethodMst, Long> {

	@Query(value = "SELECT * FROM MASTER_ANNUITY_PAYOUT_METHOD WHERE ANNUITY_PAYOUT_METHOD_CODE = :annuityPayoutMethodCode", nativeQuery = true)
	Optional<AnnuityPayoutMethodMst> findByCode(String annuityPayoutMethodCode);

}
