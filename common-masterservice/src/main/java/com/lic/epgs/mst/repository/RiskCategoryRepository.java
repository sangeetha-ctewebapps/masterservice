package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RiskCategoryMaster;

@Repository
public interface RiskCategoryRepository extends JpaRepository<RiskCategoryMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_RISK_CATEGORY WHERE RISK_CATEGORY_CODE = :riskCategoryCode", nativeQuery = true)
	Optional<RiskCategoryMaster> findByCode(String riskCategoryCode);

}
