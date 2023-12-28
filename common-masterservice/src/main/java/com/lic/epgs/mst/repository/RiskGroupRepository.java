package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RiskGroupMaster;

@Repository
public interface RiskGroupRepository extends JpaRepository<RiskGroupMaster, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM MASTER_RISK_GROUP WHERE RISK_GROUP_CODE = :riskGroupCode",
	 * nativeQuery = true) Optional<RiskGroupMaster> findByCode(String
	 * riskGroupCode);
	 */
	
	@Query("FROM RiskGroupMaster WHERE riskGroupCode = :code")
	public Optional<RiskGroupMaster> findByCode(String code);

}
