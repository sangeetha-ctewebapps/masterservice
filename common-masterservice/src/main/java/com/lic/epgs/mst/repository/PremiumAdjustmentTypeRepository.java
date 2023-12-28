package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.PremiumAdjustmentTypeMaster;

@Repository
public interface PremiumAdjustmentTypeRepository extends JpaRepository<PremiumAdjustmentTypeMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_PREMIUM_ADJUSTMENT_TYPE WHERE PREMIUM_TYPE_CODE = :premiumTypeCode", nativeQuery = true)
	Optional<PremiumAdjustmentTypeMaster> findByCode(String premiumTypeCode);

}
